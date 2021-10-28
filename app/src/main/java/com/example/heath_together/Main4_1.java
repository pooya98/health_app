package com.example.heath_together;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.heath_together.Adapter.ProfileListViewAdapter;
import com.example.heath_together.Object.DTO.ProfileListItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class Main4_1 extends Fragment {

    MaterialCalendarView calendarView;
    TextView whenDate;
    String date;

    //임시 데이터
    Map<String, String[]> date_table;

    String date_list[][] = {
            {"데드리프트", "스쿼트", "레그 익스텐션"},
            {"푸쉬업", "벤치프레스", "덤벨 프레사"},
            {"풀업", "T바 로우", "바벨 로루"}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main4_1, container, false);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView4);
        whenDate = (TextView) view.findViewById(R.id.whenDate);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);


        //todayDate
        date = sdf.format(calendar.getTime()).toString();

        //create hash map
        date_table = new HashMap<>();
        date_table.put("2021/10/23", date_list[0]);
        date_table.put("2021/10/15", date_list[1]);
        date_table.put("2021/10/17", date_list[2]);



//      Calendar Decorator
        ArrayList<CalendarDay> calendarDayList = new ArrayList<CalendarDay>();

//        calendarDayList.add(CalendarDay.today());
        //month 0~11
        Set<String> keySet = date_table.keySet();
        for (String key : keySet){
            List<String> strKey = Arrays.asList(key.split("/"));
            calendarDayList.add(CalendarDay.from(Integer.parseInt(strKey.get(0)), Integer.parseInt(strKey.get(1))-1, Integer.parseInt(strKey.get(2))));
        }
//        calendarDayList.add(CalendarDay.from(2021, 9, 23));
//        calendarDayList.add(CalendarDay.from(2021, 9, 15));
//        calendarDayList.add(CalendarDay.from(2021, 9, 17));

        EventDecorator eventDecorator = new EventDecorator(calendarDayList, getActivity());
        calendarView.addDecorators(eventDecorator);




        ListView listView = (ListView) view.findViewById(R.id.main4_1_listView);
        ProfileListViewAdapter adapter = new ProfileListViewAdapter();

        if (date_table.containsKey(date)) {
            for (int i = 0; i < date_table.get(date).length; i++) {
                adapter.addItem(new ProfileListItem(date_table.get(date)[i], "2"));
            }
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
        }

        //setText in calendar label
        whenDate.setText(date);


        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth();
                int day = date.getDay();
                String data = Year + "/" + (Month + 1) + "/" + day;

                adapter.resetItme();
                adapter.notifyDataSetChanged();

                if (date_table.containsKey(data)) {
                    for (int i = 0; i < date_table.get(data).length; i++) {
                        adapter.addItem(new ProfileListItem(date_table.get(data)[i], "2"));
                    }
                    Log.d("test", "True case");

                    listView.setAdapter(adapter);
                }
                setListViewHeightBasedOnChildren(listView);
                whenDate.setText(data);
            }
        });
        return view;
    }




    //scroll view 안에 listView 높이 조절 함수
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}