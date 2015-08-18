package train.xjtuse.keepfit;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 蓝聪聪 on 2015-08-14.
 */
public class PlanListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnCreateContextMenuListener {

    private Activity context;
    private List<Plan> planList;
    private CheckBox.OnCheckedChangeListener changeListener;

    public PlanListAdapter(Activity context,List<Plan> planList,CheckBox.OnCheckedChangeListener changeListener){
        this.context = context;
        this.planList = planList;
        this.changeListener = changeListener;
    }

    @Override
    public int getGroupCount() {
        return planList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return planList.get(groupPosition).getSubPlanViewCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return planList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return planList.get(groupPosition).getSubPlanForView(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ((long) groupPosition) << 32;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((long) childPosition) | 0x80000000l;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.plan_list_group, null);
            ((CheckBox) convertView.findViewById(R.id.plan_enabled_checkBox)).setOnCheckedChangeListener(changeListener);
        }
        ((TextView) convertView.findViewById(R.id.plan_name_text)).setText(planList.get(groupPosition).getName());
        ((TextView) convertView.findViewById(R.id.plan_frequency_text)).setText("每周"+ planList.get(groupPosition).getFrequency()+"次");
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.plan_enabled_checkBox);
        checkBox.setTag(groupPosition);
        checkBox.setChecked(planList.get(groupPosition).isActive());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.plan_list_item, null);
        }
        Plan plan = planList.get(groupPosition).getSubPlanForView(childPosition);
        if (plan!=null){
            ((TextView) convertView.findViewById(R.id.plan_subitme_name_text)).setText(plan.getName());
            ((TextView) convertView.findViewById(R.id.plan_subitme_count_text)).setText("每组"+plan.getTime()+"个，共"+plan.getGroup()+"组");
        }else{
            ((TextView) convertView.findViewById(R.id.plan_subitme_name_text)).setText("轮换计划：");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        if ((info.id & 0x80000000l) == 0) {
            context.getMenuInflater().inflate(R.menu.plan_list_context_menu, menu);
        }
    }
}
