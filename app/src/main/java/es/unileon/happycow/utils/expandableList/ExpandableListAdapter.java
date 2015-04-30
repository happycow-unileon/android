package es.unileon.happycow.utils.expandableList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

import es.unileon.happycow.utils.list.EntradaLista;

/**
 * Created by dorian on 1/05/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private List<EntradaLista> header;
    private HashMap<EntradaLista, List<EntradaLista>> childs;

    public ExpandableListAdapter(Context context, List<EntradaLista> listDataHeader,
                                 HashMap<EntradaLista, List<EntradaLista>> listChildData){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        header=listDataHeader;
        childs=listChildData;
    }

    @Override
    public int getGroupCount() {
        return header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childs.get(this.header.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childs.get(this.header.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        return header.get(groupPosition).onView(inflater, view, viewGroup);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        return childs.get(header.get(groupPosition)).get(childPosition).onView(inflater, view, viewGroup);
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
