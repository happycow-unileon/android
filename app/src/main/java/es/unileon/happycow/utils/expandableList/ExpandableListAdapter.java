package es.unileon.happycow.utils.expandableList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.rows.EntradaHeader;

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

    public void addHeader(EntradaLista headerAdd){
        if(!header.contains(headerAdd)){
            header.add(headerAdd);
        }
    }

    public void addChild(EntradaLista headerAdd, EntradaLista child){
        addHeader(headerAdd);
        List<EntradaLista> list=childs.get(headerAdd);
        if(list==null){
            list=new LinkedList<>();
            list.add(child);
            childs.put(headerAdd, list);
        }else{
            list.add(child);
            childs.put(headerAdd, list);
        }

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
    public Object getChild(int groupPosition, int childPosition) {
        return this.childs.get(this.header.get(groupPosition))
                .get(childPosition);
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
