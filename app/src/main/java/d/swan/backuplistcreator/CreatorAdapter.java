package d.swan.backuplistcreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class CreatorAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Element> values;

    public CreatorAdapter(Context context, ArrayList<Element> values) {
        this.context = context;
        this.values = values;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // метод возврата выделеного элемента
    public Element getElement(int position) {
        return ((Element) getItem(position));
    }

    // создание элемента
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null)
            view = layoutInflater.inflate(R.layout.row, parent, false);

        Element element = getElement(position);
        // присваивание свойств элементу
        ((ImageView) view.findViewById(R.id.ivIcon)).setImageResource(element.icon);
        ((TextView) view.findViewById(R.id.tvName)).setText(element.name.getName());
        ((TextView) view.findViewById(R.id.tvType)).setText(element.type);
        ((ImageView) view.findViewById(R.id.ivCheck)).setImageResource(element.check);

        view.setTag(position);
        return view;
    }

    // метод создания списка выделеных элементов
    public ArrayList<File> getSelected() {
        ArrayList<File> selList = new ArrayList<>();
        for(Element e : values)
            if(e.check == R.drawable.checked)
                selList.add(e.name);

        return selList;
    }

    // метод определения и смены выделения элемента
    public void Inverse(int tag) {
        for(Element e : values) {
            if(e.check != R.drawable.empty)
                e.check = tag;
            notifyDataSetChanged();
        }
    }
}
