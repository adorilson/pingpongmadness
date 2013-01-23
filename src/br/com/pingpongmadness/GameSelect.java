package br.com.pingpongmadness;

import br.com.pingpongmadness.database.DatabaseAdapter;
import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class GameSelect extends TabActivity implements TabHost.TabContentFactory {
	
	// LOGS
    private static final String TAG = "[PPMT]";
    private static final String TAG_NAME = "[GameSelect] ";
	
	// ABAS
    private static final String TAB_TUTORIAL = "1";
    private static final String TAB_LEVEL = "2";
    private static String mCurrentTAB = "";

	// ESTRELAS
    private static final int STAR_NONE = R.drawable.star_none;
    private static final int STAR_HALF = R.drawable.star_half;
    private static final int STAR_FULL = R.drawable.star_full;
	
    // VIEWS
    private ListView mListView = null;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "criando gameselect...");
		
        super.onCreate(savedInstanceState);
        Log.d(TAG, "gameselect criado");
        
       setContentView(R.layout.level_list);
       Log.d(TAG, "conteudo setado");

       // listview que mostrará os levels
        mListView = (ListView) findViewById(R.id.level_list);
        
        // Adicionado na parte 7 do tutorial
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Log.d(TAG, TAG_NAME + "onItemClick - " + arg3);
                Toast.makeText(getBaseContext(), "Selected item: id=" + arg3, Toast.LENGTH_SHORT).show();

                // TODO: iniciar o jogo neste level
            }
        });
        
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec tabspec;
        
        // inicializa e adiciona a aba tutorial
        tabspec = tabhost.newTabSpec(TAB_TUTORIAL);
        tabspec.setContent(this);
        tabspec.setIndicator(getText(R.string.mode_tutorial), getResources().getDrawable(STAR_NONE));
        tabhost.addTab(tabspec);
        
        Log.d(TAG, "aba tutorial adicionada...");
        
        // inicializa e adiciona a aba level
        tabspec = tabhost.newTabSpec(TAB_LEVEL);
        tabspec.setContent(this);
        tabspec.setIndicator(getText(R.string.mode_level), getResources().getDrawable(STAR_NONE));
        tabhost.addTab(tabspec);
        
        Log.d(TAG, "aba level adicionada...");
        
        // adiciona um listener para a mudanca de aba
        tabhost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.d(TAG, TAG_NAME + "onTabChanged - " + tabId);
                mCurrentTAB = tabId;

                // mostra a lista de levels
                showLevelList(tabId);               
            }
        });
        Log.d(TAG, "listener criado...");
    }

	@Override
	public View createTabContent(String tag) {
		mCurrentTAB = tag;
        showLevelList(mCurrentTAB);
        return mListView;
	}
	
	// #FEATURE Persistencia em banco de dados
	// mostra a lista de levels por tipo
    private void showLevelList(String type) {
        Cursor tutorialCursor = DatabaseAdapter.fetchLevelsByType(this, Integer.valueOf(type));
        startManagingCursor(tutorialCursor);

        // seta o adapter do listview
        mListView.setAdapter(new LevelAdapter(this, tutorialCursor, new String[] {}, new int[] {}));
    }
    
    // retorna informações do level para cada posição
    // colunas: 0=LEVEL_ID, 1=LEVEL_TYPE, 2=LEVEL_SHOTS, 3=LEVEL_NAME
    private String getLevelInfo(int position, int column) {
        return ((Cursor) ((LevelAdapter) mListView.getAdapter()).getItem(position)).getString(column);
    }
    
    // custom adapter com nome do level, pontuação, porcentagem e estrela
    private class LevelAdapter extends SimpleCursorAdapter {

    	private ContentValues recordInfo = null;
    	private Activity mActivity;
    	private ImageView mStar;
    	private TextView mScore;
    	private TextView mRatio;
    	private TextView mLevel;
    	private int misses = 0;
        private int total = 0;
    	
    	LevelAdapter(Activity context, Cursor c, String[] from, int[] to) {
            super(context, R.layout.level_row, c, from, to);
            this.mActivity = context;
        }
    	
    	public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = mActivity.getLayoutInflater();
                convertView = inflater.inflate(R.layout.level_row, null);
            }

            // seta o nome do level (coluna 3)
            mLevel = (TextView) convertView.findViewById(R.id.row_level);
            mLevel.setText(getLevelInfo(position, 3));

            // seta a imagem da estrela padrão
            mStar = (ImageView) convertView.findViewById(R.id.row_star);
            mStar.setImageResource(STAR_NONE);

            // seta a pontuação padrão
            mScore = (TextView) convertView.findViewById(R.id.row_score);
            mScore.setText("-");

            // seta a porcentagem de acertos padrão
            mRatio = (TextView) convertView.findViewById(R.id.row_ratio);
            mRatio.setText("-");

            // retorna informações do recorde deste level
            recordInfo = DatabaseAdapter.fetchRecord(getBaseContext(), getLevelInfo(position, 0));
            if (recordInfo != null && recordInfo.size() > 0) {
                misses = recordInfo.getAsInteger(DatabaseAdapter.RECORD_MISSES);
                total = Integer.valueOf(getLevelInfo(position, 2));

                if (misses > 0) {
                    // se houver qualquer erro, mostrar a meia estrela
                    mStar.setImageResource(STAR_HALF);
                }
                else {
                    // se não houver erros, mostrar a estrela cheia
                    mStar.setImageResource(STAR_FULL);
                }

                // mostra a pontuação e a porcentagem de acertos
                if (total != 0) {
                    mScore.setText(recordInfo.getAsString(DatabaseAdapter.RECORD_SCORE) + " " + getText(R.string.points));
                    mRatio.setText(((total - misses) * 100 / total) + "%");
                }

            }

            return convertView;
        }
    	
    }

}
