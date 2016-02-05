package tk.azertyfun.dasmtc.emulator;

public class M35FD extends DCPUHardware {
	public static final int TYPE = 0x4fd524c5, REVISION = 0x000b, MANUFACTURER = 0x1eb37e91;

	public static final int TRACKS = 80, SECTORS_PER_TRACK = 18, WORDS_PER_SECTOR = 512;

	public static final int WORDS_PER_SECOND = 30700;
	public static final float SEEKING_TIME_MS = 2.4f;

	protected char state = 0x0000, error = 0x0000;
	protected boolean interrupt = false;

	protected char[] disk = new char[WORDS_PER_SECTOR * SECTORS_PER_TRACK * TRACKS];

	protected boolean reading = false;
	protected boolean writing = false;
	protected char readFrom, readTo, writeFrom, writeTo;

	protected M35FD(String id, char[] disk) {
		super(TYPE, REVISION, MANUFACTURER);
		this.id = id;

		if(disk.length > this.disk.length) {
			throw new IllegalArgumentException("M35FD #" + id + ": given disk file is bigger than " + this.disk.length + " words!");
		} else {
			for(int i = 0; i < disk.length; ++i) {
				this.disk[i] = disk[i];
			}

			for(int i = disk.length; i < this.disk.length; ++i) {
				this.disk[i] = 0;
			}
		}

		state = States.STATE_READY;
		error = Errors.ERROR_NONE;
	}

	protected M35FD(String id) {
		super(TYPE, REVISION, MANUFACTURER);
		this.id = id;

		state = States.STATE_NO_MEDIA;
		error = Errors.ERROR_NO_MEDIA;
	}

	@Override
	public void interrupt() {

		int a = dcpu.registers[0];
		switch(a) {
			case 0: //POLL_DEVICE
				dcpu.registers[1] = state;
				dcpu.registers[2] = error;
				break;
			case 1: //SET_INTERRUPT
				char x = dcpu.registers[3];
				if(x != 0)
					interrupt = true;
				else
					interrupt = false;
				break;
			case 2: //READ_SECTOR
				readFrom = dcpu.registers[3];
				readTo = dcpu.registers[4];
				if(state != States.STATE_READY && state != States.STATE_READY_WP && readFrom >= SECTORS_PER_TRACK * TRACKS) {
					dcpu.registers[1] = 0;
					break;
				}

				dcpu.registers[1] = 1;
				state = States.STATE_BUSY;
				reading = true;

				break;
			case 3: //WRITE_SECTOR
				writeFrom = dcpu.registers[4];
				writeTo = dcpu.registers[3];
				if(state != States.STATE_READY && state != States.STATE_READY_WP && writeTo >= SECTORS_PER_TRACK * TRACKS) {
					dcpu.registers[1] = 0;
					break;
				}

				dcpu.registers[1] = 1;
				state = States.STATE_BUSY;
				writing = true;

				break;
		}
	}

	@Override
	public void tick60hz() {
		/**
		 * The reading should ideally be spanned over several ticks. However, the drive reads a sector per tick and seek time is way less than a tick, so we don't need to.
		 */

		if(reading) {
			for(int i = 0; i < WORDS_PER_SECTOR; ++i) {
				dcpu.ram[readTo + i] = disk[readFrom * WORDS_PER_SECTOR + i];
			}

			state = States.STATE_READY;
			reading = false;
		} else if(writing) {
			for(int i = 0; i < WORDS_PER_SECTOR; ++i) {
				disk[writeTo * WORDS_PER_SECTOR + i] = dcpu.ram[writeFrom + i];
			}

			state = States.STATE_READY;
			writing = false;
		}
	}

	@Override
	public void powerOff() {
		disk = new char[WORDS_PER_SECTOR * SECTORS_PER_TRACK * TRACKS];
		state = 0x0000;
		error = 0x0000;
		interrupt = false;
	}

	public char[] getDisk() {
		return disk;
	}

	public static class States {
		public static final int STATE_NO_MEDIA =   0x0000;
		public static final int STATE_READY =      0x0001;
		public static final int STATE_READY_WP =   0x0002;
		public static final int STATE_BUSY =       0x0003;
	}

	public static final class Errors {
		public static final int ERROR_NONE =       0x0000;
		public static final int ERROR_BUSY =       0x0001;
		public static final int ERROR_NO_MEDIA =   0x0002;
		public static final int ERROR_PROTECTED =  0x0003;
		public static final int ERROR_EJECT =      0x0004;
		public static final int ERROR_BAD_SECTOR = 0x0005;
		public static final int ERROR_BROKEN =     0xFFFF;
	}
}
