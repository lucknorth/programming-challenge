package q1;


import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int disNum=0;
		int like=0;
		ArrayList<Integer> disLikeNum = new ArrayList<Integer>();
		String tmp = br.readLine();
		while (tmp.isEmpty()){
			tmp = br.readLine();
		}
		//1行目
		String[] tmpInput = tmp.split(" ");
		disNum = Integer.parseInt(tmpInput[0]);
		like = Integer.parseInt(tmpInput[1]);

		//2行目
		tmpInput = (br.readLine()).split(" ");
		for(int i=0;i<disNum;i++){
			disLikeNum.add(Integer.parseInt(tmpInput[i]));
		}
		Collections.sort(disLikeNum);

		//約数を求める
		ArrayList<Integer> likeDivi = divisorSearch(like);

		System.out.println(notDiviSerch(likeDivi, disLikeNum));
	}



	private static ArrayList<Integer> divisorSearch(int ori){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=2;i<=ori;i++){
			if(ori%i==0){
				list.add(i);
			}
		}
		return list;
	}

	private static String notDiviSerch(ArrayList<Integer> likeDiv,ArrayList<Integer> disLikeNum){
		Iterator<Integer> likeIterator = likeDiv.iterator();
		Iterator<Integer> disLikeIterator = disLikeNum.iterator();
		while(likeIterator.hasNext()){
			int like = likeIterator.next();
			while(disLikeIterator.hasNext()){
				int disLike = disLikeIterator.next();
				if(disLike < like){
					disLikeIterator.remove();
				}
				if(disLike % like == 0){
					likeIterator.remove();
					break;
				}
			}
		}
		return String.valueOf(likeDiv.size());
	}

}
