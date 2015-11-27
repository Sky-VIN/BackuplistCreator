package d.swan.backuplistcreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {

    private File path = new File("/");
    private Fill fill = new Fill();
    private ArrayList<Element> listFiles = new ArrayList<>();
    private CreatorAdapter adapter;
    private int CheckAll = R.drawable.checked;

    CheckBox chbSub;
    TextView tvPath;
    ImageView ivCheckAll, ivRefresh, ivCreate;
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        chbSub = (CheckBox) findViewById(R.id.chbSub);
        ivCreate = (ImageView) findViewById(R.id.ivCreate);
        ivCreate.setOnClickListener(this);
        ivCheckAll = (ImageView) findViewById(R.id.ivCheckAll);
        ivCheckAll.setOnClickListener(this);
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        ivRefresh.setOnClickListener(this);
        lvMain = (ListView) findViewById(R.id.lvMain);
        tvPath = (TextView) findViewById(R.id.tvPath);
        tvPath.setText(path.toString());

        lvMain.setOnItemClickListener(this);
        lvMain.setOnItemLongClickListener(this);
        Browse(path);
    }

    // короткое нажатие на элемент
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // если элемент с именем "..", вызывается заполнение списка родительской папки
        if(adapter.getElement((Integer) view.getTag()).check == R.drawable.empty) {
            ivCheckAll.setImageResource(R.drawable.unchecked_all);
            Browse(path.getParentFile());
        } else { // меняется значение выделения
            if(adapter.getElement((Integer) view.getTag()).check == R.drawable.checked)
                adapter.getElement((Integer) view.getTag()).check = R.drawable.unchecked;
            else
                adapter.getElement((Integer) view.getTag()).check = R.drawable.checked;
            adapter.notifyDataSetChanged();
        }
    }

    // долгое нажатие на элемент
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // если у элемента значек выделения пустой, вызывается заполнение списка родительской папки
        if(adapter.getElement((Integer) view.getTag()).check == R.drawable.empty) {
            ivCheckAll.setImageResource(R.drawable.unchecked_all);
            Browse(path.getParentFile());
        }
        // если папка, вызывается заполнение списка этой папки
        else if(listFiles.get(position).name.isDirectory()) {
            ivCheckAll.setImageResource(R.drawable.unchecked_all);
            Browse(listFiles.get(position).name);
        }
        else { // если файл, вызывается стороння программа по типу файла
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(listFiles.get(position).name), new FileTypes().GetUriType(listFiles.get(position).name));
            startActivity(intent);
        }
        return true;
    }

    // нажатия на кнопки
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            // если кнопка "выделить все"
            case R.id.ivCheckAll:
                // если все выделено, то отменяет все выделения
                if(CheckAll == R.drawable.checked) {
                    CheckAll = R.drawable.unchecked;
                    ivCheckAll.setImageResource(R.drawable.checked_all);
                }
                // если все отменено, то все выделяет
                else {
                    CheckAll = R.drawable.checked;
                    ivCheckAll.setImageResource(R.drawable.unchecked_all);
                }

                // вызывает метод адаптера для выделения / снятия выделения
                adapter.Inverse(CheckAll);
                break;

            // если кнопка "обновление"
            case R.id.ivRefresh:
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
                //меняет кнопку выделения на unchecked_all
                ivCheckAll.setImageResource(R.drawable.unchecked_all);
                // заполняет тот же список, что и был
                Browse(path);
                break;

            // если кнопка создания списка
            case R.id.ivCreate:
                String message;
                try {
                    // вызыват метод создания списка и возвращает результат
                    message = new CreateList().Create(adapter.getSelected(), path, chbSub.isChecked());
                } catch (IOException e) {
                    message = e.getMessage();
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                // обновляет список
                onClick(ivRefresh);
                break;
        }

    }

    private void Browse(File path) {
        this.path = path;

        listFiles.clear(); // очищает список элементов
        listFiles.addAll(fill.ListFill(path)); // заполняет список элементов
        tvPath.setText(path.toString()); // обновляет путь

        adapter = new CreatorAdapter(this, listFiles); // создания адаптера для нового списка
        lvMain.setAdapter(adapter); // присваивает адаптер списку
    }

    @Override
    public void onBackPressed() {
        // если корневая папка, вызывает диалог завершения
        if(path.getParent() == null)
            new AlertDialog.Builder(this)
                    .setTitle("VIN: BLC")
                    .setMessage("Confirm exit")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        else Browse(new File(path.getParent())); // если не корневая, заполняет список родительской папки
    }
}
