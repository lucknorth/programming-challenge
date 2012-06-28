package q2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Solution {



	public static void main(String[] args) throws Exception{
		//reader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//マシン数
		int machineNum=0;
		//都市数
		int townNum=0;

		/** 経路のリスト */
		ArrayList<Road> roadList = new ArrayList<Road>();
		/** 都市のリスト */
		ArrayList<Town> townList = new ArrayList<Town>();
		/** 機械のいる都市の番号リスト */
		int[] machineTown;

		//1行目
		String[] tmpInput = (br.readLine()).split(" ");
		townNum = Integer.parseInt(tmpInput[0]);
		machineNum = Integer.parseInt(tmpInput[1]);
		machineTown = new int[machineNum];

		//都市リストの作成
		for(int i=0;i<townNum;i++){
			townList.add(new Town(i));
		}

		//通路の取得と生成
		for(int i=0;i<townNum-1;i++){
			Road tmpRoad = new Road(br.readLine());
			roadList.add(tmpRoad);
			//都市にインスタンスを設定
			Town tmpTown1 = townList.get(tmpRoad.getTown1());
			tmpTown1.setRoad(tmpRoad);

			Town tmpTown2 =  townList.get(tmpRoad.getTown2());
			tmpTown2.setRoad(tmpRoad);

			tmpTown1.setTown(tmpTown2);
			tmpTown2.setTown(tmpTown1);
		}

		//機械の居る都市を設定
		for(int i=0;i<machineNum;i++){
			int tmpIndex = Integer.parseInt(br.readLine());
			townList.get(tmpIndex).setMachineExists( true );
			machineTown[i]=tmpIndex;
		}

		//経路を壊す
		for(int i=0;i<machineNum-1;i++){
			breakRoad(townList.get(machineTown[i]));
		}
	}

	/** 経路を壊す */
	private static int breakRoad(Town town) {
		int weight=0;
		for(int i=0;i<town.roadNum;i++){
			Road tmpRoad = town.getIndexRoad(i);
			if(tmpRoad.isBreak == false){
				if(town.getIndexTown(i).machineExists==true){
					weight += tmpRoad.setBreak(true);
				}else{
					//探索
					weight += searchBestRoad(town,tmpRoad);
				}
			}
		}

		return 0;
	}

	/** 経路を壊す */
	private static void breakRoad(Town prev,Town town,ArrayList<Road> rList) {
		int weight=0;
		for(int i=0;i<town.roadNum;i++){
			Road tmpRoad = town.getIndexRoad(i);
			if(tmpRoad.isBreak == false){
				if(tmpRoad.getTown1() == prev.getIndex() || tmpRoad.getTown2() == prev.getIndex()){
					continue;
				}
				if(town.getIndexTown(i).machineExists==true){
					rList.add(tmpRoad);
					break;
				}else{
					//探索
//					weig searc/hBestRoad(town,tmpRoad);
				}
			}
		}
	}

	/** townからroad方向へ探索 */
	private static int searchBestRoad(Town town,Road road){
		ArrayList<Road> rList = new ArrayList<Road>();
		rList.add(road);

		Town nextTown;
		if(road.getTown1() == town.getIndex()){
			nextTown = town.getPointTown(road.getTown2());
		}else {
			nextTown =  town.getPointTown(road.getTown2());
		}
		breakRoad(town,nextTown,rList);

		return 0;
	}


	/** 都市間の経路を表す */
	static class Road {
		/** 都市 */
		private int town1;
		private int town2;

		/** 重み(経路の破壊にかかる時間) */
		private int weight;

		/** 壊れていることを表すフラグ */
		private boolean isBreak=false;


		/** "x y z"形式の文字列を引数にとる */
		public Road(String roadString){
			String[] tmp = roadString.split(" ");
			town1 = Integer.parseInt(tmp[0]);
			town2 = Integer.parseInt(tmp[1]);
			weight = Integer.parseInt(tmp[2]);
		}

		public int getTown1() {
			return town1;
		}

		public int getTown2() {
			return town2;
		}

		public int getWeight() {
			return weight;
		}

		public boolean isBreak() {
			return isBreak;
		}

		public int setBreak(boolean isBreak) {
			this.isBreak = isBreak;
			return weight;
		}
	}

	/** 都市を表す */
	static class Town{
		/**都市番号*/
		private int index;

		/** 経路のリスト */
		ArrayList<Road> roadList = new ArrayList<Road>();

		/** 繋がっている都市のリスト */
		ArrayList<Town> townList = new ArrayList<Town>();

		/** 持っている経路の数 */
		private int roadNum;

		/** 機械がいるかのフラグ */
		private boolean machineExists=false;

		/** 都市番号を引数にする */
		public Town(int townIndex){
			this.index = townIndex;
			this.roadNum=0;
		}

		/** 経路をセットする */
		public void setRoad(Road road){
			roadList.add(road);
			roadNum++;
		}

		/** 繋がっている都市を設定 */
		public void setTown(Town town){
			townList.add(town);
		}

		public int getIndex() {
			return index;
		}

		public Road getIndexRoad(int index){
			return roadList.get(index);
		}

		public Town getIndexTown(int index){
			return townList.get(index);
		}

		public Town getPointTown(int index){
			Iterator<Town> i = townList.iterator();
			while(i.hasNext()){
				Town t = i.next();
				if(t.getIndex() == index){
					return t;
				}
			}
			return null;
		}


		public int getRoadNum() {
			return roadNum;
		}

		public void setMachineExists(boolean b){
			this.machineExists = b;
		}

		public boolean getMachineExists(){
			return machineExists;
		}
	}

}
