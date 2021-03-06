/**
 *   920 Text Editor is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   920 Text Editor is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with 920 Text Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jecelyin.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.rfo.e920.R;
import com.jecelyin.widget.RfoColorPickerView;
import java.util.ArrayList;
import java.util.Arrays;

public class RfoColorPicker extends Dialog {
    public interface OnColorChangedListener {
        void onColorChanged(String mKey, String color);
    }

    private OnColorChangedListener mListener;
    private int mDefaultColor;
    private String mKey;
    private String mTitle;
    private RfoColorPickerView mColorView;
    private EditText colorEditText;

    public RfoColorPicker(Context context, OnColorChangedListener listener, String key, String title, int defaultColor) {
        super(context);
        mTitle = title;
        mListener = listener;
        mKey = key;
        mDefaultColor = defaultColor;
    }
    
    /**
     * 转换整数颜色值为字符串格式：#ffff00
     * @param color
     * @return 注，因为android前2位表示透明值，所以可以截取掉，保留6位即可
     */
    public static String getColor(int color)
    {
        String alpha = String.valueOf(Color.alpha(color));
        String red   = String.valueOf(Color.red(color));
        String green = String.valueOf(Color.green(color));
        String blue  = String.valueOf(Color.blue(color));
		return "Gr.Color " + alpha + ", " + red + ", " + green + ", " + blue;
        //return "#"+Integer.toHexString(color).substring(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new ColorPickerView(getContext(), l, mInitialColor, mDefaultColor));
        setContentView(R.layout.rfo_color_picker);
        setTitle(mTitle);
        colorEditText = (EditText) findViewById(R.id.color_text);
        colorEditText.setText(getColor(mDefaultColor));
        mColorView = (RfoColorPickerView) findViewById(R.id.rfo_color_picker_view);
        mColorView.setColor(mDefaultColor);
        mColorView.setOnColorChangedListener(mOnColorChangedListener);
        Button ok = (Button)findViewById(R.id.ok);
        Button cancel = (Button)findViewById(R.id.cancel);
        ok.setOnClickListener(onOkClickListener);
        cancel.setOnClickListener(onCancelClickListener);
    }
    
    private View.OnClickListener onOkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String color = colorEditText.getText().toString().trim();
            if("".equals(color))
                return;
            
            String test = color.toLowerCase().replaceFirst("gr.color", "").trim() + ",";
			ArrayList cols = new ArrayList(Arrays.asList(test.split("\\s*,\\s*")));
			for (int i=0; i < cols.size(); i++) {
				String argb = cols.get(i).toString();
				int c = -1;
				try { c = Integer.parseInt(argb); } catch (NumberFormatException e) { break; }
				if (c >= 0 && c <= 255)
					test = test.replaceFirst(argb + "\\s*,\\s*", "");
			}
			if (test.trim() != "") {
                Toast.makeText(RfoColorPicker.this.getContext(), R.string.invalid_color, Toast.LENGTH_LONG).show();
                return ;
            }

            mListener.onColorChanged(mKey, color);
            dismiss();
        }
    };
    
    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        public void onClick(View v)
        {
            dismiss();
        }
    };
    
    private OnColorChangedListener mOnColorChangedListener = new OnColorChangedListener() {
        public void onColorChanged(String key, String color) {
            colorEditText.setText(color);
        }
    };
}