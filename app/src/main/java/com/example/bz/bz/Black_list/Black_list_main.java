package com.example.bz.bz.Black_list;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bz.bz.R;

import java.util.List;

/**
 * Created by ChenSiyuan on 2018/9/3.
 */

public class Black_list_main extends ListActivity {
    public ListView listView;
    private ListViewAdapter bla;
    private List<BlackNumber> data;
    private int position;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.black_list_layout);
//        Toast.makeText(Black_list_main.this,"老子进来了！",Toast.LENGTH_SHORT).show();
        listView=getListView();
        //获取数据集合
        BlackNumberDAO bn=new BlackNumberDAO(this);
        data=bn.getAll();
        //适配器设置
        bla=new ListViewAdapter();
        listView.setAdapter(bla);
        //设置上下文菜单
        listView.setOnCreateContextMenuListener(this);
    }
    //创建长按菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo){
        //添加两个item
        menu.add(0,1,1,"修改");
        menu.add(0,2,2,"删除");
        //获取长按的适配器信息
        AdapterView.AdapterContextMenuInfo menuInfo1=(AdapterView.AdapterContextMenuInfo)menuInfo;
        position=menuInfo1.position;
        super.onCreateContextMenu(menu,v,menuInfo);
    }
    //添加点击事件
    @Override
    public boolean onContextItemSelected(MenuItem item){
        final BlackNumber bt = data.get(position);
        switch (item.getItemId())
        {
            //修改
            case 1:
                LayoutInflater inflater=getLayoutInflater();
                final View v = inflater.inflate(R.layout.black_list_box,null);

                final EditText editText_1 = (EditText)v.findViewById(R.id.et_bt_1);
                final EditText editText_2 = (EditText)v.findViewById(R.id.et_bt_2);
                //获取默认值
                editText_1.setText(bt.getRemark());
                editText_2.setText(bt.getPhoneNumber());

                new AlertDialog.Builder(this)
                        .setView(v)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog,int which){
                                //获取修改后的值
                                bt.setRemark(editText_1.getText().toString());
                                bt.setPhoneNumber(editText_2.getText().toString());
                                //执行修改数据库操作
                                BlackNumberDAO blackNumberDAO = new BlackNumberDAO(Black_list_main.this);
                                blackNumberDAO.update(bt);
                                //通知刷新列表
                                bla.notifyDataSetChanged();
                                Toast.makeText(Black_list_main.this,"修改成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false)
                        .show();

                break;
            //删除
            case 2:
                new AlertDialog.Builder(this)
                        .setMessage("确定要删除吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //在数据库中删除，获取id
                                BlackNumberDAO blackNumberDAO = new BlackNumberDAO(Black_list_main.this);

                                blackNumberDAO.delete(bt.getId());
                                //刷新界面
                                //移除集合中的记录
                                data.remove(position);
                                //通知刷新
                                bla.notifyDataSetChanged();

                                Toast.makeText(Black_list_main.this,"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
        }
        return super.onContextItemSelected(item);
    }
    class ListViewAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return data.size();
        }
        @Override
        public Object getItem(int position){
            return data.get(position);
        }
        @Override
        public long getItemId(int position){
            return data.get(position).getId();
        }
        @Override
        public View getView(int position,View convertView,ViewGroup parent){
            //判断可复用视图是否为空
            if(convertView==null)
            {
                //获取视图
                //获取layout文件
                LayoutInflater layoutInflater=getLayoutInflater();
                convertView = layoutInflater.inflate(R.layout.black_list_view,null);

            }
            //给视图绑定数据
            TextView tv_1=(TextView) convertView.findViewById(R.id.tv_bt_2);
            TextView tv_2=(TextView) convertView.findViewById(R.id.tv_bt_3);

            tv_1.setText(data.get(position).getRemark());
            tv_2.setText(data.get(position).getPhoneNumber());
            return convertView;
        }
    }
    //添加数据按钮
    public void add_onclick(View view){
        //获取加载器
        LayoutInflater layoutInflater = getLayoutInflater();
        //加载布局
        final View v = layoutInflater.inflate(R.layout.black_list_box,null);
        new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(false)
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText_1 = (EditText)v.findViewById(R.id.et_bt_1);
                        EditText editText_2 = (EditText)v.findViewById(R.id.et_bt_2);

                        String remark = editText_1.getText().toString();
                        String number = editText_2.getText().toString();

                        //构造实体类 参数为输入的电话号码
                        BlackNumber blackNumber = new BlackNumber(remark,number);
                        BlackNumberDAO blackNumberDAO = new BlackNumberDAO(Black_list_main.this);

                        //保存数据
                        blackNumberDAO.insert(blackNumber);
                        //添加数据到集合
                        data.add(0,blackNumber);
                        //通知适配器刷新ListView
                        bla.notifyDataSetChanged();
                    }
                })
                .show();
    }
    //全部删除
    public void delete_all(View view){
        new AlertDialog.Builder(this)
                .setMessage("确定要全部删除吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //在数据库中删除
                        BlackNumberDAO blackNumberDAO = new BlackNumberDAO(Black_list_main.this);
                        blackNumberDAO.deleteAll();
                        //刷新界面
                        //移除集合中记录
                        while (position<data.size()){
                            data.remove(position);
                        }
                        //通知刷新
                        bla.notifyDataSetChanged();
                        Toast.makeText(Black_list_main.this,"黑名单已清空",Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
