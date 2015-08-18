package train.xjtuse.keepfit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements CheckBox.OnCheckedChangeListener {

    private static String TAG = "method";
    
    private ArrayList<HashMap<String,Object>> grouplist;
    private ArrayList<ArrayList<HashMap<String,Object>>> itemlist;
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
    }

    public void savePlanList(){

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
