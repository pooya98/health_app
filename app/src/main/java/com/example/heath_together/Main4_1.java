package com.example.heath_together;

import static android.content.ContentValues.TAG;

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

import com.example.heath_together.Adapter.CalendarListViewAdapter;
import com.example.heath_together.Adapter.ProfileListViewAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ExerciseCompleteListItem;
import com.example.heath_together.Object.DTO.ProfileListItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class Main4_1 extends Fragment {

    MaterialCalendarView calendarView;
    TextView whenDate;
    ListView listView;

    Map<String, Object> calendar_data;

    ArrayList<CalendarDay> calendarDayList = new ArrayList<CalendarDay>();
    CalendarListViewAdapter adapter;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

    //todayDate
    String today = sdf.format(calendar.getTime()).toString();
    String profileId;
    String profileUserId;
    public Main4_1(String userId){

        this.profileUserId= userId;

    }
    public Main4_1(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(profileUserId==null){
            profileId=UserInfo.user_Id;
        }else{                                                 //내 계정, 상대 계정 프로필 구분.
            profileId=profileUserId;
        }

        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("calendar").document(profileId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    if(document.exists()){
                        calendar_data = document.getData();
                        if (calendar_data.containsKey(today)){
                            ArrayList<Map<String, Object>> data = (ArrayList<Map<String, Object>>) calendar_data.get(today);

                            for(Map<String ,Object> item : data){
                                Log.d(TAG, "get Item" + item.get("exerciseName"));
                            }


                            if (calendar_data.containsKey(today)){
                                ArrayList<Map<String, Object>> exercise = (ArrayList<Map<String, Object>>) calendar_data.get(today);
                                for(Map<String, Object> item : exercise){
                                    adapter.addItem(new ExerciseCompleteListItem(item.get("exerciseName").toString(), item.get("set").toString()));
                                }
                                listView.setAdapter(adapter);
                                setListViewHeightBasedOnChildren(listView);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        for (String key : calendar_data.keySet() ){
                            List<String> strKey = Arrays.asList(key.split("-"));
                            calendarDayList.add(CalendarDay.from(Integer.parseInt(strKey.get(0)), Integer.parseInt(strKey.get(1))-1 , Integer.parseInt(strKey.get(2))));
                        }

                        EventDecorator eventDecorator = new EventDecorator(calendarDayList, getActivity());
                        calendarView.addDecorators(eventDecorator);
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "main4 onCreateView");
        View view = inflater.inflate(R.layout.main4_1, container, false);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView4);
        whenDate = (TextView) view.findViewById(R.id.whenDate);
        listView = (ListView) view.findViewById(R.id.main4_1_listView);

        adapter = new CalendarListViewAdapter();


        //setText in calendar label
        whenDate.setText(today);


        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth();
                String day = null;

                if (date.getDay() < 10){
                    day = "0" + date.getDay();
                } else {
                    day = String.valueOf(date.getDay());
                }

                String c_day = Year + "-" + (Month + 1) + "-" + day;


                adapter.resetItme();
                adapter.notifyDataSetChanged();

                if (calendar_data.containsKey(c_day)) {
                    ArrayList<Map<String, Object>> exercise = (ArrayList<Map<String, Object>>) calendar_data.get(c_day);
                    for(Map<String, Object> item : exercise){
                        adapter.addItem(new ExerciseCompleteListItem(item.get("exerciseName").toString(), item.get("set").toString()));
                    }
                    listView.setAdapter(adapter);
                }
                setListViewHeightBasedOnChildren(listView);
                whenDate.setText(c_day);
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