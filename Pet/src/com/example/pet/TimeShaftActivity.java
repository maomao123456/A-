package com.example.pet;

import java.util.ArrayList;
import java.util.List;

import com.example.pet.baseadapter.TimeShaftListviewAdapter;
import com.example.pet.lei.SquareGridview;
import com.example.pet.lei.TimeShaftListview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

public class TimeShaftActivity extends Activity{
	ListView listview;
	LayoutInflater inflater;
	View itemview;
	GridView gridview;
	List<TimeShaftListview> list;
	TimeShaftListview timeShaft;
	TimeShaftListviewAdapter adapter;
	List<SquareGridview> list_gridview;
	SquareGridview squareGridview;
	int[] id={R.id.head_listview_timeshaft,R.id.time_listview_timeshaft,
			R.id.word_listview_timeshaft,R.id.gridview_listview_timeshaft};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeshaft_dongtai);
		init();
		getList();
		adapter=new TimeShaftListviewAdapter(this, list, 
				R.layout.listview_item_timeshaft, id);
		listview.setAdapter(adapter);
	}
	public void init(){
		listview=(ListView)findViewById(R.id.timeshaft_listview);
		inflater=this.getLayoutInflater();
		itemview=inflater.inflate(R.layout.listview_item_timeshaft, null);
		gridview=(GridView)itemview.findViewById(R.id.gridview_listview_timeshaft);
	}
	/**
	 * 给listView添加数据
	 */
	int[] heads={R.drawable.circle_head3,R.drawable.circle_head4,R.drawable.circle_head5
			,R.drawable.circle_head6};
	int[] pics={R.drawable.time_memory1,R.drawable.time_memory2,R.drawable.time_memory3,
			R.drawable.time_memory4,R.drawable.time_memory5,R.drawable.time_memory6};
	public void getList(){
		list=new ArrayList<TimeShaftListview>();
		list_gridview=new ArrayList<SquareGridview>();
		squareGridview=new SquareGridview();
		squareGridview.setImage(pics[0]);
		list_gridview.add(squareGridview);
		timeShaft=new TimeShaftListview();
		timeShaft.setHead(heads[0]);
		timeShaft.setTime("2016.05.10");
		timeShaft.setWord("我与毛毛相遇在这个夏天");
		timeShaft.setList(list_gridview);
		list.add(timeShaft);
		
		list_gridview=new ArrayList<SquareGridview>();
		squareGridview=new SquareGridview();
		squareGridview.setImage(pics[1]);
		list_gridview.add(squareGridview);
		squareGridview=new SquareGridview();
		squareGridview.setImage(pics[2]);
		list_gridview.add(squareGridview);
		timeShaft=new TimeShaftListview();
		timeShaft.setHead(heads[1]);
		timeShaft.setTime("2016.06.10");
		timeShaft.setWord("毛毛与我玩的很开心");
		timeShaft.setList(list_gridview);
		list.add(timeShaft);
		
		list_gridview=new ArrayList<SquareGridview>();
		squareGridview=new SquareGridview();
		squareGridview.setImage(pics[3]);
		list_gridview.add(squareGridview);
		squareGridview=new SquareGridview();
		squareGridview.setImage(pics[4]);
		list_gridview.add(squareGridview);
		squareGridview=new SquareGridview();
		squareGridview.setImage(pics[5]);
		list_gridview.add(squareGridview);
		timeShaft=new TimeShaftListview();
		timeShaft.setHead(heads[2]);
		timeShaft.setTime("2016.07.10");
		timeShaft.setWord("毛毛长大了");
		timeShaft.setList(list_gridview);
		list.add(timeShaft);
		
		list_gridview=new ArrayList<SquareGridview>();
		timeShaft=new TimeShaftListview();
		timeShaft.setHead(heads[3]);
		timeShaft.setTime("2016.08.10");
		timeShaft.setWord("毛毛的快乐时光");
		timeShaft.setList(list_gridview);
		list.add(timeShaft);
	}
}
