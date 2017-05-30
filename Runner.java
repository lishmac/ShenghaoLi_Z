package pkg1;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Runner {
	public static void main(String[] args){
		TopN t1 = new TopN();
		//sample input from your example
		String[] log = new String[]{"U1", "/", "U1", "subscribers", "U2", "/","U2", "subscribers", "U1", "filter", "U1", "export", "U2", "filter", "U2", "export", "U3", "/", "U3", "catalog", "U3", "edit"};
		String[] log2 = new String[]{"U1", "/", "U1", "sub", "U1", "export"};
		int N = 2;
		Map<String, List<String>> output1 = t1.preProcessing(log);
		Map<String, Integer> output2 = t1.frequencyMap(output1);
		String[] result = t1.topNPath(output2, N);
		System.out.println(Arrays.toString(result));
	}
}
