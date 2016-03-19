package tk.azertyfun.dcputoolchain.assembler;

import tk.azertyfun.dcputoolchain.assembler.sourceManagement.Line;
import tk.azertyfun.dcputoolchain.assembler.sourceManagement.SourceManager;


public class LabelAggregator {
	private SourceManager	sourceManager;


	public LabelAggregator(SourceManager sourceManager){
		this.sourceManager = sourceManager;
	}

	public void aggregateLabels(){
		for(Line l: sourceManager.getLines()){
			String[] splitted = l.getLine().split(" ");

			if(splitted[0].equalsIgnoreCase(".LBL") || splitted[0].equalsIgnoreCase("#LBL")){
				sourceManager.addLabel(splitted[1], l);
			}
		}
	}
}
