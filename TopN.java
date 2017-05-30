package pkg1;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TopN {
	//concatenate user's visiting path into a list
	//String[] log;
	public Map<String, List<String>> preProcessing(String[] log){
		Map<String, List<String>> record = new HashMap<String, List<String>>();
		//check corner case
		if (log == null){
			return record;
		}
		//iterate through the log, concatenate user visiting paths together
		for (int i=0; i<log.length-1; i=i+2){
			String user = log[i];
			String path = log[i+1];
			if (record.containsKey(user)){
				record.get(user).add(path);
			}
			else {
				List<String> current = new ArrayList<String>();
				current.add(path);
				record.put(user, current);
			}
		}
		return record;
	}
	
	//convert all user's visiting path into a frequency map
	public Map<String, Integer> frequencyMap(Map<String, List<String>> input){
		Map<String, Integer> frequencyMap = new HashMap<>();
		//check corner case
		if (input == null){
			return frequencyMap;
		}
		
		for (String x:input.keySet()){
			List<String> visitHistory = input.get(x);
			//if user visit path is shorter than 3
			if (visitHistory.size() < 3){
				continue;
			}
			
			for (int i=0; i<=visitHistory.size()-3; i++){
				String threePath = visitHistory.get(i)+"->"+visitHistory.get(i+1)+"->"+visitHistory.get(i+2);
				Integer count = frequencyMap.get(threePath);
				if (count == null){
					frequencyMap.put(threePath, 1);
				}
				else {
					frequencyMap.put(threePath, count+1);
				}
			}
		}
		return frequencyMap;
	}
	
	//get the top N 3 length path
	public String[] topNPath(Map<String, Integer> frequencyMap, int N){
		//to get the top N path I'm using a minHeap with size of N
		PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(N, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		
		//based on the size of the heap and the value of the entry to determine
		//whether to swap the current element with the top element of the heap or not
		for (Map.Entry<String, Integer> e: frequencyMap.entrySet()){
			if (minHeap.size() < N){
				minHeap.offer(e);
			}
			else {
				if (e.getValue() > minHeap.peek().getValue()){
					minHeap.poll();
					minHeap.offer(e);
				}
			}
		}
		
		String[] result = new String[minHeap.size()];
		//this can ensure the result array is in ascending order
		for (int i=minHeap.size()-1; i>=0; i--){
			result[i] = minHeap.poll().getKey();
		}
		
		return result;
	}
	
}
