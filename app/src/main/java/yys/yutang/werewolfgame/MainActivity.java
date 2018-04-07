package yys.yutang.werewolfgame;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private List<String> s = new ArrayList<>();
    private List<Integer> n = new ArrayList<>();
    private ListView listView;
    private ArrayList<Map<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        update();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.choose_gui, null);
                final Dialog d = builder.setTitle("身份选项")
                        .setView(v)
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                String s = ((EditText) v.findViewById(R.id.sChoose)).getText().toString().trim();
                                try {
                                    int n = Integer.parseInt(((EditText) v.findViewById(R.id.numberChoose)).getText().toString().trim());
                                    MainActivity.this.s.set(position, s);
                                    MainActivity.this.n.set(position, n);
                                    update();
                                } catch (Exception ignore) {
                                    Toast.makeText(MainActivity.this, "数字不对", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create();
                d.show();
                v.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.s.remove(position);
                        MainActivity.this.n.remove(position);
                        d.cancel();
                        update();
                    }
                });
            }
        });
    }


    public void spawn(View view) {
        List<Integer> id = new ArrayList<>();
        List<String> s = new ArrayList<>();
        int total = 0;
        int index = 0;
        for (Integer integer : n) {
            total += integer;
            for (int n = 0; n < integer; n++) {
                s.add(this.s.get(index));
            }
            index++;
        }
        for (int n = 0; n < total; n++) {
            id.add(n + 1);
        }
        StringBuilder sb = new StringBuilder();
        Collections.shuffle(s);
        for (index = 0; index < id.size(); index++) {
            sb.append(id.get(index)).append(":").append(s.get(index)).append('\n');
        }
        new AlertDialog.Builder(this).setTitle("随机结果").setMessage(sb.toString()).create().show();
    }

    public void addS(View view) {
        s.add("身份");
        n.add(0);
        update();
    }

    private void update() {
        list.clear();
        for (int index = 0; index < s.size(); index++) {
            final int i = index;
            list.add(new HashMap<String, Object>() {{
                put("s", s.get(i));
                put("n", n.get(i));
            }});
        }
        listView.setAdapter(new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{"s", "n"}, new int[]{android.R.id.text1, android.R.id.text2}));
        listView.deferNotifyDataSetChanged();
    }
}
