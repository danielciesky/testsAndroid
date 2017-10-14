package com.example.macinnovacion.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TableLayout layout;

    private TableRow.OnClickListener control;
    private TableRow.OnLongClickListener borrar;
    private EditText.OnEditorActionListener fin_edit;

    private String tag_num = "";

    AlertDialog dialog;
    EditText editText;

    private static final String TAG = "MainActive";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Edita");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TextView cuarto = (TextView) layout.findViewWithTag("Text#" + tag_num);
                cuarto.setText(editText.getText());
            }
        });

        control = new TableRow.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.e(TAG,"TableRow OnClick");
            }
        };

        borrar = new TableRow.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.e(TAG,"TableRow OnLongClick");

                String tag = view.getTag().toString();
                String last = tag.substring(tag.lastIndexOf("#") +1);

                TextView cuarto = (TextView) layout.findViewWithTag("Text#" + last);

                editText.setText(cuarto.getText());

                tag_num = last;

                dialog.show();

                return true;
            }
        };



        layout = (TableLayout) findViewById(R.id.t_layout);

        //Añadiendo botones guardados
        try {
            int x = 0;
            //Agregando botones guardados
            while (x < 4) {

                x++;

                TableRow.LayoutParams rowparams = new TableRow.LayoutParams
                        (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);
                TableRow row = new TableRow(this);
                row.setGravity(Gravity.CENTER);
                row.setOnLongClickListener(borrar);
                row.setOnClickListener(control);
                row.setTag("Linea#"+ String.valueOf(x));

                //LinearLayout row = new LinearLayout(Control_1.this);
                //row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                //        ViewGroup.LayoutParams.WRAP_CONTENT));
                //row.setGravity(View.TEXT_ALIGNMENT_VIEW_END);

                //use a GradientDrawable with only one color set, to make it a solid color
                GradientDrawable border = new GradientDrawable();
                border.setColor(0x00000000); //white background
                border.setCornerRadius(20);
                border.setStroke(1, 0xFF000000); //black border with full opacity
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    row.setBackgroundDrawable(border);
                } else {
                    row.setBackground(border);
                }

                //Imagen de habitación

                ImageView room = new ImageView(this);
                room.setLayoutParams(rowparams);
                room.setBackgroundColor(0x00000000);
                room.setTag("Room#"+ String.valueOf(x));//room.setId(cursor.getInt(0));
                //room.setOnClickListener(control);
                //room.setBackgroundColor(0xFF000000);
                int imageRes = getResources().getIdentifier("@mipmap/ic_launcher_round", null, getPackageName());
                Drawable res;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    res = getResources().getDrawable(imageRes, null);
                } else {
                    res = getResources().getDrawable(imageRes);
                }
                room.setImageDrawable(res);
                row.addView(room);

                //Agregando etiqueta editable

                TextView text = new TextView(this);
                text.setLayoutParams(rowparams);
                text.setTag("Text#" + String.valueOf(x));
                //text.setInputType(InputType.TYPE_NULL);//text.setEnabled(true);
                //text.setSingleLine();
                text.setText("TEST");
                text.setGravity(Gravity.CENTER);
                //text.setOnLongClickListener(name);
                //text.setOnEditorActionListener(fin_edit);
                text.setBackgroundColor(0x00000000);
                text.setTextColor(0xFFFFFFFF);
                row.addView(text);

//                //Botón de edición
//
//                ImageButton edit_ok = new ImageButton(this);
//                edit_ok.setLayoutParams(rowparams);
//
//                edit_ok.setTag("Image#" + String.valueOf(cursor.getInt(0)));
//                edit_ok.setOnClickListener(fin_edit);
//                edit_ok.setVisibility(View.GONE);
//                edit_ok.setBackgroundColor(0xFFFFFFFF);
//                edit_ok.setImageResource(R.drawable.check);
//
//                row.addView(edit_ok);

                //Indicador de dispositivos

                TextView text2 = new TextView(this);
                text2.setLayoutParams(rowparams);
                text2.setTag("Txt#" + String.valueOf(x));
                //text2.setInputType(InputType.TYPE_NULL);//text.setEnabled(true);
                //text2.setSingleLine();
                //Cursor cursor2 = db2.rawQuery("select sw_room from switchDB where sw_room = '"+cursor.getString(1)+"'",null);
                text2.setText(String.valueOf(x));
                text2.setGravity(Gravity.CENTER);
                text2.setBackgroundColor(0x00000000);
                text2.setTextColor(0xFFFFFFFF);

                row.addView(text2);

                layout.addView(row);

            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}
