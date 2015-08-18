package train.xjtuse.keepfit;

import java.util.ArrayList;

/**
 * Created by 蓝聪聪 on 2015-08-18.
 */
public class Plan {
    public enum PlanType {Plan,SubPlan};
    private int id;
    private String name;
    private ArrayList<Plan> subPlanList;
    private ArrayList<Plan> subPlanList2;
    private int group;
    private int time;
    private boolean active;
    private int frequency;

    public int getSubPlanCount(){
        int size = 0;
        if (subPlanList!=null) size+=subPlanList.size();
        if (subPlanList2!=null) size+=subPlanList2.size();
        return size;
    }

    public int getSubPlanViewCount(){
        int size = 0;
        if (subPlanList!=null) size+=subPlanList.size();
        if (subPlanList2!=null) size+=subPlanList2.size()+1;
        return size;
    }

    public ArrayList<Plan> getSubPlanList() {
        return subPlanList;
    }

    public ArrayList<Plan> getSubPlanList(int day) {
        if (day % 2 == 1){
            return subPlanList2;
        }
        return subPlanList;
    }

    public ArrayList<Plan> getSubPlanList2() {
        return subPlanList2;
    }

    public void setSubPlanList(ArrayList<Plan> subPlanList) {
        this.subPlanList = subPlanList;
    }

    public void setSubPlanList2(ArrayList<Plan> subPlanList2) {
        this.subPlanList2 = subPlanList2;
    }

    public Plan getSubPlan(int position){
        if (position>=subPlanList.size()){
            return subPlanList2.get(position-subPlanList.size());
        }else{
            return  subPlanList.get(position);
        }
    }

    public Plan getSubPlanForView(int position){
        if (position>subPlanList.size()){
            return subPlanList2.get(position-subPlanList.size() - 1);
        }else if (position<subPlanList.size()){
            return  subPlanList.get(position);
        }else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public PlanType getPlanType(){
        if (subPlanList!=null) return PlanType.Plan;
        return PlanType.SubPlan;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
