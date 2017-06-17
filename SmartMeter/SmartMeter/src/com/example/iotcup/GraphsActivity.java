package com.example.iotcup;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.androidplot.xy.*;
import com.androidplot.Plot;
import com.androidplot.xy.SimpleXYSeries;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;

public class GraphsActivity extends Activity {

	private List<MeterReadingInformationObject> meterReadingInformationObjects;

	private int selectedReportType;
	XYPlot xyPlot;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_activity);
        
        xyPlot = (XYPlot) findViewById(R.id.xyPlot);
        
        selectedReportType = getIntent().getExtras().getInt("selectedReportType");
        
        meterReadingInformationObjects = new ArrayList<MeterReadingInformationObject>();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        	meterReadingInformationObjects = bundle.getParcelableArrayList("meterReadingInformationObjects");
        
        Collections.sort(meterReadingInformationObjects);
        
        ShowGraphs();
        
    }
		
	private void ShowGraphs() {
		switch(selectedReportType) {
			default:
			case 1:
				List<Number> sumActive = new ArrayList<Number>();
				for(int i=0;i<meterReadingInformationObjects.size();i++) {
					MeterReadingInformationObject meterReadingInformationObject = meterReadingInformationObjects.get(i);
					sumActive.add(meterReadingInformationObject.GetSumActive());
				}
				setup(sumActive,Color.rgb(200, 200, 0),2,true);
		        xyPlot.setRangeLabel("توان حقیقی");
				break;
				
			case 2:
				List<Number> sumReactive = new ArrayList<Number>();
				for(int i=0;i<meterReadingInformationObjects.size();i++) {
					MeterReadingInformationObject meterReadingInformationObject = meterReadingInformationObjects.get(i);
					sumReactive.add(meterReadingInformationObject.GetSumReactive());
				}
				setup(sumReactive,Color.rgb(200, 200, 0),2,true);
		        xyPlot.setRangeLabel("توان راکتیو");
				break;
				
			case 3:
				List<Number> L1Voltage = new ArrayList<Number>();
				List<Number> L2Voltage = new ArrayList<Number>();
				List<Number> L3Voltage = new ArrayList<Number>();
				for(int i=0;i<meterReadingInformationObjects.size();i++) {
					MeterReadingInformationObject meterReadingInformationObject = meterReadingInformationObjects.get(i);
					L1Voltage.add(meterReadingInformationObject.GetL1Voltage());
					L2Voltage.add(meterReadingInformationObject.GetL2Voltage());
					L3Voltage.add(meterReadingInformationObject.GetL3Voltage());
				}
				setup(L1Voltage,Color.rgb(200, 200, 0),1,false);
				setup(L2Voltage,Color.rgb(0, 200, 200),1,false);
				setup(L3Voltage,Color.rgb(200, 0, 200),1,false);
		        xyPlot.setRangeLabel("ولتاژ");
				break;
				
			case 4:
				List<Number> L1Current = new ArrayList<Number>();
				List<Number> L2Current = new ArrayList<Number>();
				List<Number> L3Current = new ArrayList<Number>();
				for(int i=0;i<meterReadingInformationObjects.size();i++) {
					MeterReadingInformationObject meterReadingInformationObject = meterReadingInformationObjects.get(i);
					L1Current.add(meterReadingInformationObject.GetL1Current());
					L2Current.add(meterReadingInformationObject.GetL2Current());
					L3Current.add(meterReadingInformationObject.GetL3Current());
				}
				setup(L1Current,Color.rgb(200, 200, 0),1,false);
				setup(L2Current,Color.rgb(0, 200, 200),1,false);
				setup(L3Current,Color.rgb(200, 0, 200),1,false);
		        xyPlot.setRangeLabel("جریان");
				break;
				
		}
		
		//rangeValues.add(5); rangeValues.add(8); rangeValues.add(9); rangeValues.add(2); rangeValues.add(5);
		//domainValues.add(978307200); domainValues.add(1009843200); domainValues.add(1041379200); domainValues.add(1072915200); domainValues.add(1104537600);

		xyPlot.getLayoutManager().remove(xyPlot.getDomainLabelWidget());
		xyPlot.getLayoutManager().remove(xyPlot.getRangeLabelWidget());
		xyPlot.getLayoutManager().remove(xyPlot.getLegendWidget());
		xyPlot.setPlotMargins(0,0,0,0);
		xyPlot.setGridPadding(20, 30, 30, 20);
		xyPlot.setGridPadding(5,5,5,5);
		xyPlot.getLegendWidget().setVisible(false);
        xyPlot.setDomainLabel("زمان");
        xyPlot.setRangeValueFormat(new DecimalFormat("0"));
        xyPlot.setDomainValueFormat(new Format() {
			private static final long serialVersionUID = 189360417160970218L;
	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd hh:mm",java.util.Locale.getDefault());
 
			@Override
			public StringBuffer format(Object obj, StringBuffer toAppendTo,FieldPosition pos) {
				long timestamp = ((Number) obj).longValue() * 1000;
                Date date = new Date(timestamp);
            	
                return simpleDateFormat.format(date, toAppendTo, pos);
			}

			@Override
			public Object parseObject(String source, ParsePosition pos) {
				return null;
			}
        });
        
        xyPlot.getLayoutManager().remove(xyPlot.getLegendWidget());
        xyPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        xyPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        xyPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        xyPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        xyPlot.getBorderPaint().setStrokeWidth(3);
        xyPlot.getBorderPaint().setAntiAlias(false);
        xyPlot.getBorderPaint().setColor(Color.WHITE);
        
	}
   
	private void setup(List<Number> rangeValues, int color, int mode, boolean fill) {
		List<Number> domainValues = new ArrayList<Number>();
		for(int i=0;i<meterReadingInformationObjects.size();i++) {
			MeterReadingInformationObject meterReadingInformationObject = meterReadingInformationObjects.get(i);
			domainValues.add((long)(meterReadingInformationObject.GetTime().getTime().getTime()/1000));
		}
		XYSeries xySeries = new SimpleXYSeries(domainValues,rangeValues,"");
		
		int domainSize = domainValues.size();
		if(domainValues.size()>=10)
			domainSize = 10;	
        xyPlot.setDomainStep(XYStepMode.SUBDIVIDE, domainSize);
        
		if(mode==1) {
			LineAndPointFormatter lineAndPointFormatter = new LineAndPointFormatter(color,color,null,new PointLabelFormatter(Color.BLACK));
	        if(fill) {
				Paint paint = new Paint();
		        paint.setAlpha(200);
		        paint.setShader(new LinearGradient(0, 0, 0, 250, Color.YELLOW, Color.RED, Shader.TileMode.MIRROR));
		        lineAndPointFormatter.setFillPaint(paint);
	        }
	        lineAndPointFormatter.setPointLabelFormatter(null);
			xyPlot.addSeries(xySeries, lineAndPointFormatter);
		}
		else if(mode==2) {
			StepFormatter stepFormatter  = new StepFormatter(Color.rgb(0, 0,0), color);
	        stepFormatter.getLinePaint().setStrokeWidth(1);
	        stepFormatter.getVertexPaint().setColor(Color.GRAY);
	        Paint lineFill = new Paint();
	        lineFill.setAlpha(200);
	        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.YELLOW, Color.RED, Shader.TileMode.MIRROR));
	        stepFormatter.setFillPaint(lineFill);
			xyPlot.addSeries(xySeries, stepFormatter);
		}
		
	}
	
}
