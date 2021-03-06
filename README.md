DCPU Emulator & Assembler
=========================

This is an emulator/debugger and a (buggy) assembler for the techcompliant DCPU (formerly notch's in 0x10^c).

Supported hardware
------------------

* DCPU;
* CPU-Control (IACM);
* Clock;
* Keyboard;
* LEM1802;
* EDC;
* M35FD.

Planned hardware
----------------

Everything planned by [Paul](https://github.com/paultech/TC-Specs).

How to build
------------

* Import with IntelliJ idea;
* Install org.lwjgl:lwjgl:3.0.0b from Maven;
* Download and add the 3.0.0b LWJGL natives from [the LWJGL downloads page](https://www.lwjgl.org/download);
* Enjoy!

Usage
-----

* To run a program: `run <file> [--assemble] [--debugger] [--little-endian] [--clock] [--keyboard] [--lem1802] [--edc] [--M35FD=/path/to/file] [--M525HD=/path/to/file]`: Runs emulator for <file> (binary format) with specified hardware. If the --assemble flag is set, first assembles the file in the system temp directory. Big-endian by default, unless the --little-endian switch is present. If no hardware is specified, runs with clock, keyboard and LEM1802. --debugger enables a debugger interface with run/pause/stop/step, an interface to view the RAM and registers.
* To assemble a program: `assemble <input file> <output file> [--little-endian]`: Assembles <input file> to <output file>. Big-endian by default, unless the --little-endian switch is present.

Screenshot
----------

![Screenshot of DCPU-Toolchain](https://raw.githubusercontent.com/azertyfun/DCPU-Toolchain/master/res/screenshot.png)
