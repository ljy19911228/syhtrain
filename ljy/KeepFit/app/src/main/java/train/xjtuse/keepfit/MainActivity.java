package train.xjtuse.keepfit;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends Activity implements CheckBox.OnCheckedChangeListener {

    private static String TAG = "method";

    private static int WeekDay[] = {R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday,R.id.sunday};
    
    private PlanListAdapter adapter;
    private View planView;
    private ArrayList<Plan> planList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadPlanList(){
        if (planList==null)planList = new ArrayList<Plan>();
        else planList.clear();
        SharedPreferences spf = getSharedPreferences("plan", MODE_PRIVATE);
        int plancount = spf.getInt("plancount", 0);
        for (int i=0;i<plancount;i++){
            Plan plan = new Plan();
            plan.setName(spf.getString("name" + i, ""));
            plan.setActive(spf.getBoolean("active" + i, false));
            plan.setFrequency(spf.getInt("frequency" + i, 0));
            planList.add(plan);
            int subplancount = spf.getInt("subplancount"+i,0);
            ArrayList<Plan> subplanlist = new ArrayList<Plan>();
            for (int j=0;j<subplancount;j++){
                Plan subplan = new Plan();
                subplan.setName(spf.getString("subname" + i + "," + j,""));
                subplan.setGroup(spf.getInt("group" + i + "," + j, 0));
                subplan.setTime(spf.getInt("time"+i+","+j,0));
                subplanlist.add(subplan);
            }
            planList.get(i).setSubPlanList(subplanlist);
            subplancount = spf.getInt("subplancount2,"+i,0);
            if (subplancount==0)continue;
            subplanlist = new ArrayList<Plan>();
            for (int j=0;j<subplancount;j++){
                Plan subplan = new Plan();
                subplan.setName(spf.getString("subname2," + i + "," + j,""));
                subplan.setGroup(spf.getInt("group2," + i + "," + j, 0));
                subplan.setTime(spf.getInt("time2," + i + "," + j, 0));
                subplanlist.add(subplan);
            }
            planList.get(i).setSubPlanList2(subplanlist);
        }
    }

    public void savePlanList(){
        SharedPreferences.Editor editor = getSharedPreferences("plan", MODE_PRIVATE).edit();
        editor.putInt("plancount", planList.size());
        for (int i=0;i<planList.size();i++){
            editor.putString("name" + i, planList.get(i).getName());
            editor.putBoolean("active" + i, planList.get(i).isActive());
            editor.putInt("frequency"+i, planList.get(i).getFrequency());
            editor.putInt("subplancount" + i, planList.get(i).getSubPlanList().size());
            for (int j=0;j<planList.get(i).getSubPlanList().size();j++){
                editor.putString("subname" + i + "," + j, planList.get(i).getSubPlanList().get(j).getName());
                editor.putInt("group" + i + "," + j, planList.get(i).getSubPlanList().get(j).getGroup());
                editor.putInt("time" + i + "," + j, planList.get(i).getSubPlanList().get(j).getTime());
            }
            if (planList.get(i).getSubPlanList2()==null){
                editor.putInt("subplancount2," + i,0);
                continue;
            }
            editor.putInt("subplancount2," + i, planList.get(i).getSubPlanList2().size());
            for (int j=0;j<planList.get(i).getSubPlanList2().size();j++){
                editor.putString("subname2,"+i+","+j,planList.get(i).getSubPlanList2().get(j).getName());
                editor.putInt("group2,"+i+","+j,planList.get(i).getSubPlanList2().get(j).getGroup());
                editor.putInt("time2,"+i+","+j,planList.get(i).getSubPlanList2().get(j).getTime());
            }
        }
        editor.commit();

    }

    public void toPlanManage(View v){
        loadPlanList();
        if (planView==null){
            planView = getLayoutInflater().inflate(R.layout.plan_select,null);
            ExpandableListView planList = (ExpandableListView)planView.findViewById(R.id.plan_list);
            adapter = new PlanListAdapter(this,this.planList,this);
            planList.setAdapter(adapter);
            planList.setOnCreateContextMenuListener(adapter);
        }
        setContentView(planView);
        adapter.notifyDataSetChanged();
    }

    public void toAddPlan(View V){
        setContentView(R.layout.add_plan);
    }

    public void toAddBasicPlan(View v){
        setContentView(R.layout.add_basic_plan);
    }

    public void toAddGymPlan(View v){
       setContentView(R.layout.add_gym_plan);
    }

    public void toAddHomePlan(View v){
        setContentView(R.layout.add_home_plan);
    }

    public void toCustomPlan(View v){
        Log.e(TAG, "toCustomPlan ");
    }

    public void toMain(View v){
        setContentView(R.layout.activity_main);
    }

    public void toNotificationSetting(View v){
        setContentView(R.layout.notification_setting);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        SharedPreferences spf = getSharedPreferences("notification",MODE_PRIVATE);
        timePicker.setCurrentHour(spf.getInt("hour",19));
        timePicker.setCurrentMinute(spf.getInt("minute",0));
        for (int i=0;i<7;i++){
            ((CheckBox)findViewById(WeekDay[i])).setChecked(spf.getBoolean("day"+i,false));
        }
    }

    public void setNotification(View v){
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        SharedPreferences.Editor editor = getSharedPreferences("notification",MODE_PRIVATE).edit();
        editor.putInt("hour",timePicker.getCurrentHour());
        editor.putInt("minute", timePicker.getCurrentMinute());
        for (int i = 0 ; i < 7 ; i++){
            editor.putBoolean("day"+i,((CheckBox)findViewById(WeekDay[i])).isChecked());
        }
        editor.commit();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify();
        toMain(v);
    }

    public void addBasicPlan(View v){
        loadPlanList();
        for (Plan plan : planList){
            plan.setActive(false);
        }
        Plan plan = new Plan();
        plan.setName("基础健身计划");
        plan.setActive(true);
        plan.setFrequency(3);
        planList.add(plan);
        ArrayList<Plan> sublist = new ArrayList<Plan>();
        Plan sub = new Plan();
        sub.setName("俯卧撑");
        sub.setTime(5);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("仰卧起坐");
        sub.setTime(20);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("引体向上");
        sub.setTime(2);
        sub.setGroup(2);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("长跑1000米");
        sub.setTime(1);
        sub.setGroup(1);
        sublist.add(sub);
        plan.setSubPlanList(sublist);
        savePlanList();
        toPlanManage(v);
    }

    public void addGymPlan(View v){
        loadPlanList();
        for (Plan plan : planList){
            plan.setActive(false);
        }
        Plan plan = new Plan();
        plan.setName("健身房计划");
        plan.setActive(true);
        plan.setFrequency(3);
        planList.add(plan);
        ArrayList<Plan> sublist = new ArrayList<Plan>();
        Plan sub = new Plan();
        sub.setName("斜板/平板卧推");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("坐姿蝴蝶夹胸/坐姿推胸");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("杠铃弯举");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("坐姿蹬腿/腿屈伸弯举");
        sub.setTime(8);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("史密斯深蹲/站姿提踵");
        sub.setTime(10);
        sub.setGroup(3);
        sublist.add(sub);sub = new Plan();
        sub.setName("卷腹/举腿");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        plan.setSubPlanList(sublist);
        sublist = new ArrayList<Plan>();
        sub = new Plan();
        sub.setName("高位下拉/坐姿划船");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("山羊挺身");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("钢线下压/杠铃臂屈伸");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("坐姿蹬腿/腿屈伸弯举");
        sub.setTime(8);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("史密斯深蹲/站姿提踵");
        sub.setTime(10);
        sub.setGroup(3);
        sublist.add(sub);sub = new Plan();
        sub.setName("卷腹/举腿");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        plan.setSubPlanList2(sublist);
        savePlanList();
        toPlanManage(v);
    }

    public void addHomePlan(View v){
        loadPlanList();
        for (Plan plan : planList){
            plan.setActive(false);
        }
        Plan plan = new Plan();
        plan.setName("家庭健身计划");
        plan.setActive(true);
        plan.setFrequency(3);
        planList.add(plan);
        ArrayList<Plan> sublist = new ArrayList<Plan>();
        Plan sub = new Plan();
        sub.setName("哑铃飞鸟");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("斜板/平板哑铃卧推");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("杠铃弯举");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("哑铃深蹲");
        sub.setTime(8);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("哑铃提踵");
        sub.setTime(10);
        sub.setGroup(3);
        sublist.add(sub);sub = new Plan();
        sub.setName("仰卧起坐/仰卧举腿");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        plan.setSubPlanList(sublist);
        sublist = new ArrayList<Plan>();
        sub = new Plan();
        sub.setName("引体向上");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("哑铃划船");
        sub.setTime(8);
        sub.setGroup(3);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("钢线下压/杠铃臂屈伸");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("哑铃深蹲");
        sub.setTime(8);
        sub.setGroup(4);
        sublist.add(sub);
        sub = new Plan();
        sub.setName("哑铃提踵");
        sub.setTime(10);
        sub.setGroup(3);
        sublist.add(sub);sub = new Plan();
        sub.setName("仰卧起坐/仰卧举腿");
        sub.setTime(12);
        sub.setGroup(4);
        sublist.add(sub);
        plan.setSubPlanList2(sublist);
        savePlanList();
        toPlanManage(v);
    }

    public void addCustomPlan(View v){
        Log.e(TAG, "addCustomPlan ");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.plan_remove){
            ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
            planList.remove((int) (info.id >> 32));
            adapter.notifyDataSetChanged();
            savePlanList();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (Integer) buttonView.getTag();
        boolean enabled = (Boolean) planList.get(position).isActive();
        if (enabled && !isChecked) {
            ((CheckBox) buttonView).setChecked(enabled);
        } else if (!enabled && isChecked) {
            for (Plan p : planList) {
                p.setActive(false);
            }
            planList.get(position).setActive(true);
            savePlanList();
            adapter.notifyDataSetChanged();
        }
    }
}
