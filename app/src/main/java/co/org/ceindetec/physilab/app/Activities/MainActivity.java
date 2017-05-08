package co.org.ceindetec.physilab.app.Activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import co.org.ceindetec.physilab.R;

public class MainActivity extends AppCompatActivity {

    WebSocketClient mWebSocketClient;

    double confAngulo;
    double confMasa;
    double confDistancia;
    double confLongitud;
    double confRadio;

    double datosRecibidosTiempo;
    double datosRecibidosGiros;
    double datosRecibidosOscilaciones;

    double periodoCalculado;
    double gravedadCalculada;
    double frecuenciaCalculada;
    double velocidadCalculada;
    double aceleracionCalculada;
    double velocidadAngularCalculada;
    double velocidadTangencialCalculada;
    double aceleracionCentripetaCalculada;
    double distanciaCalculada;

    String periodoUsuario;
    String gravedadUsuario;
    String frecuenciaUsuario;
    String velocidadUsuario;
    String aceleracionUsuario;
    String velocidadAngularUsuario;
    String velocidadTangencialUsuario;
    String aceleracionCentripetaUsuario;
    String distanciaUsuario;

    JSONObject messageJson;
    JSONObject messageInicial;

    TextView txtConexion;

    LinearLayout llyLogo;
    RelativeLayout rlyConexion;
    RelativeLayout rlyModulosControl;
    RelativeLayout rlyModulosControlBack;
    RelativeLayout rlyModulosPlanoInclinado;
    RelativeLayout rlyModulosPenduloSimple;
    RelativeLayout rlyModulosMovimientoCircular;

    EditText edtConfAnguloPI;
    EditText edtConfDistanciaPI;

    EditText edtConfRadioMC;

    EditText edtConfAnguloPS;
    EditText edtConfMasaPS;
    EditText edtConfLongitudPS;

    EditText edtDatosTiempoPI;

    EditText edtDatosTiempoMC;
    EditText edtDatosGirosMC;

    EditText edtDatosTiempoPS;
    EditText edtDatosCantOscilacionesPS;

    EditText edtResultFrecuenciaPS;
    EditText edtResultGravedadPS;
    EditText edtResultPeriodoPS;

    ImageView imgResultFrecuenciaPS;
    ImageView imgResultGravedadPS;
    ImageView imgResultPeriodoPS;

    EditText edtResultVelocidadPI;
    EditText edtResultAceleracionPI;

    ImageView imgResultVelocidadPI;
    ImageView imgResultAceleracionPI;

    EditText edtResultVangularMC;
    EditText edtResultVtangencialMC;
    EditText edtResultAcentriMC;
    EditText edtResultDistanciaMC;

    ImageView imgResultVangularMC;
    ImageView imgResultVtangencialMC;
    ImageView imgResultAcentriMC;
    ImageView imgResultDistanciaMC;

    Button btnRecDatosMC;
    Button btnRecDatosPS;
    Button btnRecDatosPI;

    Button btnGuardarConfMC;
    Button btnGuardarConfPS;
    Button btnGuardarConfPI;

    Button btnEditarConfMC;
    Button btnEditarConfPS;
    Button btnEditarConfPI;

    Button btnResultPS;
    Button btnResultMC;
    Button btnResultPI;

    TabHost tabHost;
    TabHost.TabSpec tabSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectWebSocket();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtConexion = (TextView) findViewById(R.id.txtConexion);

        llyLogo = (LinearLayout) findViewById(R.id.llyLogo);
        rlyConexion = (RelativeLayout) findViewById(R.id.rlyConexion);
        rlyModulosControl = (RelativeLayout) findViewById(R.id.rlyModulosControl);
        rlyModulosControlBack = (RelativeLayout) findViewById(R.id.rlyModulosControlBack);
        rlyModulosPlanoInclinado = (RelativeLayout) findViewById(R.id.rlyModulosPlanoInclinado);
        rlyModulosPenduloSimple = (RelativeLayout) findViewById(R.id.rlyModulosPenduloSimple);
        rlyModulosMovimientoCircular = (RelativeLayout) findViewById(R.id.rlyModulosMovimientoCircular);

        edtConfAnguloPI = (EditText) findViewById(R.id.edtConfiguracionAnguloPlanoInclinado);
        edtConfDistanciaPI = (EditText) findViewById(R.id.edtConfiguracionDistanciaPlanoInclinado);

        edtConfRadioMC = (EditText) findViewById(R.id.edtConfiguracionRadioMovimientoCircular);

        edtConfAnguloPS = (EditText) findViewById(R.id.edtConfiguracionAnguloPenduloSimple);
        edtConfMasaPS = (EditText) findViewById(R.id.edtConfiguracionMasaPenduloSimple);
        edtConfLongitudPS = (EditText) findViewById(R.id.edtConfiguracionLongitudPenduloSimple);

        edtDatosTiempoPI = (EditText) findViewById(R.id.edtDatosTiempoPlanoInclinado);

        edtDatosTiempoMC = (EditText) findViewById(R.id.edtDatosTiempoMovimientoCircular);
        edtDatosGirosMC = (EditText) findViewById(R.id.edtDatosGirosMovimientoCircular);

        edtDatosTiempoPS = (EditText) findViewById(R.id.edtDatosTiempoPenduloSimple);
        edtDatosCantOscilacionesPS = (EditText) findViewById(R.id.edtDatosCantidadOscilacionesPenduloSimple);

        edtResultFrecuenciaPS = (EditText) findViewById(R.id.edtResultadosFrecuenciaPenduloSimple);
        edtResultGravedadPS = (EditText) findViewById(R.id.edtResultadosGravedadPenduloSimple);
        edtResultPeriodoPS = (EditText) findViewById(R.id.edtResultadosPeriodoPenduloSimple);

        imgResultFrecuenciaPS = (ImageView) findViewById(R.id.imgResultadosFrecuenciaPenduloSimple);
        imgResultGravedadPS = (ImageView) findViewById(R.id.imgResultadosGravedadPenduloSimple);
        imgResultPeriodoPS = (ImageView) findViewById(R.id.imgResultadosPeriodoPenduloSimple);

        edtResultVelocidadPI = (EditText) findViewById(R.id.edtResultadosVelocidadPlanoInclinado);
        edtResultAceleracionPI = (EditText) findViewById(R.id.edtResultadosAceleracionPlanoInclinado);

        imgResultVelocidadPI = (ImageView) findViewById(R.id.imgResultadosVelocidadPlanoInclinado);
        imgResultAceleracionPI = (ImageView) findViewById(R.id.imgResultadosAceleracionPlanoInclinado);

        edtResultVangularMC = (EditText) findViewById(R.id.edtResultadosVangularMovimientoCircular);
        edtResultVtangencialMC = (EditText) findViewById(R.id.edtResultadosVtangencialMovimientoCircular);
        edtResultAcentriMC = (EditText) findViewById(R.id.edtResultadosAceleracionCentripetaMovimientoCircular);
        edtResultDistanciaMC = (EditText) findViewById(R.id.edtResultadosDistanciaMovimientoCircular);

        imgResultVangularMC = (ImageView) findViewById(R.id.imgResultadosVangularMovimientoCircular);
        imgResultVtangencialMC = (ImageView) findViewById(R.id.imgResultadosVtangencialMovimientoCircular);
        imgResultAcentriMC = (ImageView) findViewById(R.id.imgResultadosAceleracionCentripetaMovimientoCircular);
        imgResultDistanciaMC = (ImageView) findViewById(R.id.imgResultadosDistanciaMovimientoCircular);

        btnRecDatosMC = (Button) findViewById(R.id.btnRecibirDatosMovimientoCircular);
        btnRecDatosPS = (Button) findViewById(R.id.btnRecibirDatosPenduloSimple);
        btnRecDatosPI = (Button) findViewById(R.id.btnRecibirDatosPlanoInclinado);

        btnGuardarConfMC = (Button) findViewById(R.id.btnGuardaConfiguracionMovimientoCircular);
        btnGuardarConfPS = (Button) findViewById(R.id.btnGuardaConfiguracionPenduloSimple);
        btnGuardarConfPI = (Button) findViewById(R.id.btnGuardaConfiguracionPlanoInclinado);

        btnEditarConfMC = (Button) findViewById(R.id.btnEditarConfiguracionMovimientoCircular);
        btnEditarConfPS = (Button) findViewById(R.id.btnEditarConfiguracionPenduloSimple);
        btnEditarConfPI = (Button) findViewById(R.id.btnEditarConfiguracionPlanoInclinado);

        btnResultMC = (Button) findViewById(R.id.btnResultadosMovimientoCircular);
        btnResultPS = (Button) findViewById(R.id.btnResultadosPenduloSimple);
        btnResultPI = (Button) findViewById(R.id.btnResultadosPlanoInclinado);

        //Llenado de datos de las pestañas de movimiento Circular
        tabHost = (TabHost) findViewById(R.id.tabMovimientoCircular);
        tabHost.setup();

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_configuracion));
        tabSpec.setContent(R.id.rlyTabMovimientoCircularConfiguracion);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_configurar, null));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_recibir_datos));
        tabSpec.setContent(R.id.rlyTabMovimientoCircularDatosRecibidos);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_importar, null));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_resultados));
        tabSpec.setContent(R.id.rlyTabMovimientoCircularResultados);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_evaluar, null));
        tabHost.addTab(tabSpec);

        //Llenado de datos de las pestañas de Pendulo Simple
        tabHost = (TabHost) findViewById(R.id.tabPenduloSimple);
        tabHost.setup();

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_configuracion));
        tabSpec.setContent(R.id.rlyTabPenduloSimpleConfiguracion);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_configurar, null));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_recibir_datos));
        tabSpec.setContent(R.id.rlyTabPenduloSimpleDatosRecibidos);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_importar, null));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_resultados));
        tabSpec.setContent(R.id.rlyTabPenduloSimpleResultados);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_evaluar, null));
        tabHost.addTab(tabSpec);

        //Llenado de datos de las pestañas de Plano Inclinado
        tabHost = (TabHost) findViewById(R.id.tabPlanoInclinado);
        tabHost.setup();

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_configuracion));
        tabSpec.setContent(R.id.rlyTabPlanoInclinadoConfiguracion);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_configurar, null));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_recibir_datos));
        tabSpec.setContent(R.id.rlyTabPlanoInclinadoDatosRecibidos);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_importar, null));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.general_tab_resultados));
        tabSpec.setContent(R.id.rlyTabPlanoInclinadoResultados);
        tabSpec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_tab_evaluar, null));
        tabHost.addTab(tabSpec);

    }

    public void inicializarDatos() {

        messageJson = messageInicial;

        confAngulo = 0;
        confMasa = 0;
        confDistancia = 0;
        confLongitud = 0;
        confRadio = 0;

        datosRecibidosTiempo = 0;
        datosRecibidosGiros = 0;
        datosRecibidosOscilaciones = 0;

        periodoCalculado = 0;
        gravedadCalculada = 0;
        frecuenciaCalculada = 0;
        velocidadCalculada = 0;
        aceleracionCalculada = 0;
        velocidadAngularCalculada = 0;
        velocidadTangencialCalculada = 0;
        aceleracionCentripetaCalculada = 0;
        distanciaCalculada = 0;

        periodoUsuario = "";
        gravedadUsuario = "";
        frecuenciaUsuario = "";
        velocidadUsuario = "";
        aceleracionUsuario = "";
        velocidadAngularUsuario = "";
        velocidadTangencialUsuario = "";
        aceleracionCentripetaUsuario = "";
        distanciaUsuario = "";

        inicializarRetorno();

    }

    public void inicializarRetorno() {

        ocultarTeclado();

        //*************************************************************

        edtConfAnguloPI.setText("");
        edtConfDistanciaPI.setText("");

        edtDatosTiempoPI.setText("");

        edtResultVelocidadPI.setText("");
        edtResultVelocidadPI.setEnabled(false);
        edtResultVelocidadPI.setFocusable(false);

        edtResultAceleracionPI.setText("");
        edtResultAceleracionPI.setEnabled(false);
        edtResultAceleracionPI.setFocusable(false);

        imgResultVelocidadPI.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        imgResultAceleracionPI.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));

        btnEditarConfPI.setEnabled(false);
        btnEditarConfPI.setVisibility(View.GONE);

        btnGuardarConfPI.setEnabled(true);
        btnGuardarConfPI.setVisibility(View.VISIBLE);

        //*************************************************************

        edtConfRadioMC.setText("");

        edtDatosTiempoMC.setText("");
        edtDatosGirosMC.setText("");

        edtResultVangularMC.setText("");
        edtResultVangularMC.setEnabled(false);
        edtResultVangularMC.setFocusable(false);

        edtResultVtangencialMC.setText("");
        edtResultVtangencialMC.setEnabled(false);
        edtResultVtangencialMC.setFocusable(false);

        edtResultAcentriMC.setText("");
        edtResultAcentriMC.setEnabled(false);
        edtResultAcentriMC.setFocusable(false);

        edtResultDistanciaMC.setText("");
        edtResultDistanciaMC.setEnabled(false);
        edtResultDistanciaMC.setFocusable(false);

        imgResultVangularMC.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        imgResultVtangencialMC.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        imgResultAcentriMC.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        imgResultDistanciaMC.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));

        btnEditarConfMC.setEnabled(false);
        btnEditarConfMC.setVisibility(View.GONE);

        btnGuardarConfMC.setEnabled(true);
        btnGuardarConfMC.setVisibility(View.VISIBLE);

        //*************************************************************

        edtConfAnguloPS.setText("");
        edtConfMasaPS.setText("");
        edtConfLongitudPS.setText("");

        edtDatosTiempoPS.setText("");
        edtDatosCantOscilacionesPS.setText("");

        edtResultFrecuenciaPS.setText("");
        edtResultFrecuenciaPS.setEnabled(false);
        edtResultFrecuenciaPS.setFocusable(false);

        edtResultGravedadPS.setText("");
        edtResultGravedadPS.setEnabled(false);
        edtResultGravedadPS.setFocusable(false);

        edtResultPeriodoPS.setText("");
        edtResultPeriodoPS.setEnabled(false);
        edtResultPeriodoPS.setFocusable(false);

        imgResultFrecuenciaPS.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        imgResultGravedadPS.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        imgResultPeriodoPS.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));

        btnEditarConfPS.setEnabled(false);
        btnEditarConfPS.setVisibility(View.GONE);

        btnGuardarConfPS.setEnabled(true);
        btnGuardarConfPS.setVisibility(View.VISIBLE);

    }

    public void moduloPlanoInclinado(View view) {

        //Layouts que se muestran
        rlyModulosControlBack.setVisibility(View.VISIBLE);
        rlyModulosPlanoInclinado.setVisibility(View.VISIBLE);

        //Layouts que se ocultan
        llyLogo.setVisibility(View.GONE);
        rlyConexion.setVisibility(View.GONE);
        rlyModulosControl.setVisibility(View.GONE);
        rlyModulosPenduloSimple.setVisibility(View.GONE);
        rlyModulosMovimientoCircular.setVisibility(View.GONE);

        btnRecDatosPI.setEnabled(false);
        btnResultPI.setEnabled(false);

    }

    public void guardarConfiguracionPlanoInclinado(View view) {

        ocultarTeclado();

        if (!edtConfAnguloPI.getText().toString().equals("") &&
                !edtConfDistanciaPI.getText().toString().equals("")) {

            if (Double.parseDouble(edtConfAnguloPI.getText().toString()) >= 40 &&
                    Double.parseDouble(edtConfAnguloPI.getText().toString()) <= 5) {

                if (Double.parseDouble(edtConfDistanciaPI.getText().toString()) >= 50 &&
                        Double.parseDouble(edtConfDistanciaPI.getText().toString()) <= 100) {

                    confAngulo = Double.parseDouble(edtConfAnguloPI.getText().toString());
                    confDistancia = Double.parseDouble(edtConfDistanciaPI.getText().toString());

                    String strAnguloPenduloSimple = getString(R.string.modulo_hint_configuracionAnguloDatos, confAngulo);
                    String strLongitudPenduloSimple = getString(R.string.modulo_hint_configuracionDistanciaDatos, confDistancia);

                    edtConfAnguloPI.setText(strAnguloPenduloSimple);
                    edtConfAnguloPI.setEnabled(false);

                    edtConfDistanciaPI.setText(strLongitudPenduloSimple);
                    edtConfDistanciaPI.setEnabled(false);

                    btnGuardarConfPI.setVisibility(View.GONE);
                    btnEditarConfPI.setVisibility(View.VISIBLE);

                    btnRecDatosPI.setEnabled(true);
                } else {
                    Toast.makeText(this, R.string.pi_error_distancia, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, R.string.pi_error_angulo, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.main_error_moduloNoConfiguracion, Toast.LENGTH_LONG).show();
        }


    }

    public void editarConfiguracionPlanoInclinado(View view) {

        ocultarTeclado();

        inicializarRetorno();

        btnGuardarConfPI.setVisibility(View.VISIBLE);
        btnEditarConfPI.setVisibility(View.GONE);

        btnRecDatosPI.setEnabled(false);
        btnResultPI.setEnabled(false);

        edtConfAnguloPI.setText(String.valueOf(confAngulo));
        edtConfAnguloPI.setEnabled(true);

        edtConfDistanciaPI.setText(String.valueOf(confDistancia));
        edtConfDistanciaPI.setEnabled(true);
    }

    public void recibirDatosPlanoInclinado(View view) {

        try {
            Log.e("mensaje", messageJson.toString());
            if (messageJson.getString("modulo").trim().equals("PI")) {

                datosRecibidosTiempo = Double.parseDouble(messageJson.getString("segundos").trim() + "." + messageJson.getString("milisegundos").trim());

                String datosTiempoPlanoInclinado = messageJson.getString("segundos").trim() + "." + messageJson.getString("milisegundos").trim() + " s";

                edtDatosTiempoPI.setText(datosTiempoPlanoInclinado);

                edtResultVelocidadPI.setEnabled(true);
                edtResultVelocidadPI.setFocusable(true);

                edtResultAceleracionPI.setEnabled(true);
                edtResultAceleracionPI.setFocusable(true);

                btnResultPI.setEnabled(true);

            } else {
                if (messageJson.getString("modulo").trim().equals("")) {
                    Toast.makeText(this, R.string.main_error_moduloNoDatos, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, R.string.main_error_moduloNoCoincide, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void evaluarDatosPlanoInclinado(View view) {

        ocultarTeclado();

        if (datosRecibidosTiempo != 0) {

            velocidadUsuario = edtResultVelocidadPI.getText().toString();
            aceleracionUsuario = edtResultAceleracionPI.getText().toString();

            aceleracionCalculada = ((2 / datosRecibidosTiempo) * confDistancia)/100;
            velocidadCalculada = aceleracionCalculada * datosRecibidosTiempo;

            Log.i("acelCalculada sup", String.valueOf(tolerancia(aceleracionCalculada, 1)));
            Log.i("acelCalculada inf", String.valueOf(tolerancia(aceleracionCalculada, 2)));

            validarResultado(aceleracionUsuario, aceleracionCalculada, imgResultAceleracionPI);

            Log.i("velCalculada sup", String.valueOf(tolerancia(velocidadCalculada, 1)));
            Log.i("velCalculada inf", String.valueOf(tolerancia(velocidadCalculada, 2)));

            validarResultado(velocidadUsuario, velocidadCalculada, imgResultVelocidadPI);


        } else {
            Toast.makeText(this, getResources().getString(R.string.main_error_moduloNoDatos), Toast.LENGTH_LONG).show();
        }
    }

    public void moduloMovimientoCircular(View view) {

        //Layouts que se muestran
        rlyModulosControlBack.setVisibility(View.VISIBLE);
        rlyModulosMovimientoCircular.setVisibility(View.VISIBLE);

        //Layouts que se ocultan
        llyLogo.setVisibility(View.GONE);
        rlyConexion.setVisibility(View.GONE);
        rlyModulosControl.setVisibility(View.GONE);
        rlyModulosPenduloSimple.setVisibility(View.GONE);
        rlyModulosPlanoInclinado.setVisibility(View.GONE);

        btnRecDatosMC.setEnabled(false);
        btnResultMC.setEnabled(false);

    }

    public void guardarConfiguracionMovimientoCircular(View view) {

        ocultarTeclado();

        if (!edtConfRadioMC.getText().toString().equals("")) {

            if (Double.parseDouble(edtConfRadioMC.getText().toString()) >= 1 &&
                    Double.parseDouble(edtConfRadioMC.getText().toString()) <= 15) {

                confRadio = Double.parseDouble(edtConfRadioMC.getText().toString());

                String strRadioMovimientoCircular = getString(R.string.modulo_hint_configuracionRadioDatos, confRadio);

                edtConfRadioMC.setText(strRadioMovimientoCircular);
                edtConfRadioMC.setEnabled(false);

                btnGuardarConfMC.setVisibility(View.GONE);
                btnEditarConfMC.setVisibility(View.VISIBLE);

                btnRecDatosMC.setEnabled(true);
            } else {
                Toast.makeText(this, R.string.mc_error_radio, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.main_error_moduloNoConfiguracion, Toast.LENGTH_LONG).show();
        }

    }

    public void editarConfiguracionMovimientoCircular(View view) {

        ocultarTeclado();

        inicializarRetorno();

        btnGuardarConfMC.setVisibility(View.VISIBLE);
        btnEditarConfMC.setVisibility(View.GONE);

        btnRecDatosMC.setEnabled(false);
        btnResultMC.setEnabled(false);

        edtConfRadioMC.setText(String.valueOf(confRadio));
        edtConfRadioMC.setEnabled(true);

    }

    public void recibirDatosMovimientoCircular(View view) {

        try {
            Log.e("mensaje", messageJson.toString());
            if (messageJson.getString("modulo").trim().equals("MC")) {

                datosRecibidosTiempo = Double.parseDouble(messageJson.getString("segundos").trim() + "." + messageJson.getString("milisegundos").trim());
                datosRecibidosGiros = Double.parseDouble(messageJson.getString("numerogiros").trim());

                String datosTiempoMovimientoCircular = messageJson.getString("segundos").trim() + "." + messageJson.getString("milisegundos").trim() + " s";
                String datosGirosMovimientoCircular = messageJson.getString("numerogiros").trim() + " giros";

                edtDatosTiempoMC.setText(datosTiempoMovimientoCircular);

                edtDatosGirosMC.setText(datosGirosMovimientoCircular);

                edtResultVangularMC.setEnabled(true);
                edtResultVtangencialMC.setEnabled(true);
                edtResultAcentriMC.setEnabled(true);
                edtResultDistanciaMC.setEnabled(true);

                edtResultVangularMC.setFocusable(true);
                edtResultVtangencialMC.setFocusable(true);
                edtResultAcentriMC.setFocusable(true);
                edtResultDistanciaMC.setFocusable(true);

                btnResultMC.setEnabled(true);

            } else {
                if (messageJson.getString("modulo").trim().equals("")) {
                    Toast.makeText(this, R.string.main_error_moduloNoDatos, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, R.string.main_error_moduloNoCoincide, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void evaluarDatosMovimientoCircular(View view) {

        ocultarTeclado();

        if (datosRecibidosGiros != 0 && datosRecibidosTiempo != 0) {

            velocidadAngularUsuario = edtResultVangularMC.getText().toString();
            velocidadTangencialUsuario = edtResultVtangencialMC.getText().toString();
            aceleracionCentripetaUsuario = edtResultAcentriMC.getText().toString();
            distanciaUsuario = edtResultDistanciaMC.getText().toString();

            frecuenciaCalculada = datosRecibidosGiros / datosRecibidosTiempo;

            velocidadAngularCalculada = 2 * Math.PI * frecuenciaCalculada;
            velocidadTangencialCalculada = velocidadAngularCalculada * confRadio;
            aceleracionCentripetaCalculada = Math.pow(velocidadAngularCalculada, 2) * confRadio;
            distanciaCalculada = 2 * Math.PI * confRadio * datosRecibidosGiros;

            Log.i("velAngCalculada sup", String.valueOf(tolerancia(velocidadAngularCalculada, 1)));
            Log.i("velAngCalculada inf", String.valueOf(tolerancia(velocidadAngularCalculada, 2)));

            validarResultado(velocidadAngularUsuario, velocidadAngularCalculada, imgResultVangularMC);

            Log.i("velTangCalculada sup", String.valueOf(tolerancia(velocidadTangencialCalculada, 1)));
            Log.i("velTangCalculada inf", String.valueOf(tolerancia(velocidadTangencialCalculada, 2)));

            validarResultado(velocidadTangencialUsuario, velocidadTangencialCalculada, imgResultVtangencialMC);

            Log.i("acelCentriCalculada sup", String.valueOf(tolerancia(aceleracionCentripetaCalculada, 1)));
            Log.i("acelCentriCalculada inf", String.valueOf(tolerancia(aceleracionCentripetaCalculada, 2)));

            validarResultado(aceleracionCentripetaUsuario, aceleracionCentripetaCalculada, imgResultAcentriMC);

            Log.i("distanciaCalculada sup", String.valueOf(tolerancia(distanciaCalculada, 1)));
            Log.i("distanciaCalculada inf", String.valueOf(tolerancia(distanciaCalculada, 2)));

            validarResultado(distanciaUsuario, distanciaCalculada, imgResultDistanciaMC);


        } else {
            Toast.makeText(this, getResources().getString(R.string.main_error_moduloNoDatos), Toast.LENGTH_LONG).show();
        }
    }


    public void moduloPenduloSimple(View view) {

        //Layouts que se muestran
        rlyModulosControlBack.setVisibility(View.VISIBLE);
        rlyModulosPenduloSimple.setVisibility(View.VISIBLE);

        //Layouts que se ocultan
        llyLogo.setVisibility(View.GONE);
        rlyConexion.setVisibility(View.GONE);
        rlyModulosControl.setVisibility(View.GONE);
        rlyModulosMovimientoCircular.setVisibility(View.GONE);
        rlyModulosPlanoInclinado.setVisibility(View.GONE);

        btnRecDatosPS.setEnabled(false);
        btnResultPS.setEnabled(false);

    }

    public void guardarConfiguracionPenduloSimple(View view) {

        ocultarTeclado();

        if (!edtConfAnguloPS.getText().toString().equals("") &&
                !edtConfMasaPS.getText().toString().equals("") &&
                !edtConfLongitudPS.getText().toString().equals("")) {

            if (Double.parseDouble(edtConfAnguloPS.getText().toString()) >= 1 &&
                    Double.parseDouble(edtConfAnguloPS.getText().toString()) <= 15) {

                if (Double.parseDouble(edtConfLongitudPS.getText().toString()) >= 10 &&
                        Double.parseDouble(edtConfLongitudPS.getText().toString()) <= 50) {

                    confAngulo = Double.parseDouble(edtConfAnguloPS.getText().toString());
                    confMasa = Double.parseDouble(edtConfMasaPS.getText().toString());
                    confLongitud = Double.parseDouble(edtConfLongitudPS.getText().toString());

                    String strAnguloPenduloSimple = getString(R.string.modulo_hint_configuracionAnguloDatos, confAngulo);
                    String strMasaPenduloSimple = getString(R.string.modulo_hint_configuracionMasaDatos, confMasa);
                    String strLongitudPenduloSimple = getString(R.string.modulo_hint_configuracionLongitudDatos, confLongitud);

                    edtConfAnguloPS.setText(strAnguloPenduloSimple);
                    edtConfAnguloPS.setEnabled(false);

                    edtConfMasaPS.setText(strMasaPenduloSimple);
                    edtConfMasaPS.setEnabled(false);

                    edtConfLongitudPS.setText(strLongitudPenduloSimple);
                    edtConfLongitudPS.setEnabled(false);

                    btnGuardarConfPS.setVisibility(View.GONE);
                    btnEditarConfPS.setVisibility(View.VISIBLE);

                    btnRecDatosPS.setEnabled(true);
                } else {
                    Toast.makeText(this, R.string.ps_error_longitud, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, R.string.ps_error_angulo, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, R.string.main_error_moduloNoConfiguracion, Toast.LENGTH_LONG).show();
        }
    }

    public void editarConfiguracionPenduloSimple(View view) {

        ocultarTeclado();

        inicializarRetorno();

        btnGuardarConfPS.setVisibility(View.VISIBLE);
        btnEditarConfPS.setVisibility(View.GONE);

        btnRecDatosPS.setEnabled(false);
        btnResultPS.setEnabled(false);

        edtConfAnguloPS.setText(String.valueOf(confAngulo));
        edtConfAnguloPS.setEnabled(true);

        edtConfMasaPS.setText(String.valueOf(confMasa));
        edtConfMasaPS.setEnabled(true);

        edtConfLongitudPS.setText(String.valueOf(confLongitud));
        edtConfLongitudPS.setEnabled(true);
    }

    public void recibirDatosPenduloSimple(View view) {
        try {
            Log.e("mensaje", messageJson.toString());
            if (messageJson.getString("modulo").trim().equals("PS")) {

                datosRecibidosTiempo = Double.parseDouble(messageJson.getString("segundos").trim() + "." + messageJson.getString("milisegundos").trim());
                datosRecibidosOscilaciones = Double.parseDouble(messageJson.getString("oscilaciones").trim());

                String datosTiempoPenduloSimple = messageJson.getString("segundos").trim() + "." + messageJson.getString("milisegundos").trim() + " s";
                String datosOscilacionesPenduloSimple = messageJson.getString("oscilaciones").trim() + " osc";

                edtDatosTiempoPS.setText(datosTiempoPenduloSimple);

                edtDatosCantOscilacionesPS.setText(datosOscilacionesPenduloSimple);

                edtResultFrecuenciaPS.setEnabled(true);
                edtResultGravedadPS.setEnabled(true);
                edtResultPeriodoPS.setEnabled(true);

                edtResultFrecuenciaPS.setFocusable(true);
                edtResultGravedadPS.setFocusable(true);
                edtResultPeriodoPS.setFocusable(true);

                btnResultPS.setEnabled(true);

            } else {
                if (messageJson.getString("modulo").trim().equals("")) {
                    Toast.makeText(this, R.string.main_error_moduloNoDatos, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, R.string.main_error_moduloNoCoincide, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void evaluarDatosPenduloSimple(View view) {

        ocultarTeclado();

        if (datosRecibidosOscilaciones != 0 && datosRecibidosTiempo != 0) {

            frecuenciaUsuario = edtResultFrecuenciaPS.getText().toString();
            gravedadUsuario = edtResultGravedadPS.getText().toString();
            periodoUsuario = edtResultPeriodoPS.getText().toString();

            frecuenciaCalculada = datosRecibidosOscilaciones / datosRecibidosTiempo;
            periodoCalculado = 1 / frecuenciaCalculada;
            gravedadCalculada = ((4 * Math.pow(Math.PI, 2) * confLongitud) / (Math.pow(periodoCalculado, 2)))/100;

            Log.i("frecuenciaCalculada sup", String.valueOf(tolerancia(frecuenciaCalculada, 1)));
            Log.i("frecuenciaCalculada inf", String.valueOf(tolerancia(frecuenciaCalculada, 2)));

            validarResultado(frecuenciaUsuario, frecuenciaCalculada, imgResultFrecuenciaPS);

            Log.i("periodoCalculado sup", String.valueOf(tolerancia(periodoCalculado, 1)));
            Log.i("periodoCalculado inf", String.valueOf(tolerancia(periodoCalculado, 2)));

            validarResultado(periodoUsuario, periodoCalculado, imgResultPeriodoPS);

            Log.i("gravedadCalculada sup", String.valueOf(tolerancia(gravedadCalculada, 1)));
            Log.i("gravedadCalculada inf", String.valueOf(tolerancia(gravedadCalculada, 2)));

            validarResultado(gravedadUsuario, gravedadCalculada, imgResultGravedadPS);

            Log.i("estado enabled: ", String.valueOf(edtResultFrecuenciaPS.isClickable()));
            Log.i("estado focus: ", String.valueOf(edtResultFrecuenciaPS.isActivated()));

        } else {
            Toast.makeText(this, getResources().getString(R.string.main_error_moduloNoDatos), Toast.LENGTH_LONG).show();
        }
    }

    public void validarResultado(String datoUsuario, double datoCalculado, ImageView imageView) {

        double validaDato = 0;
        if (!datoUsuario.equals("")) {
            validaDato = Double.parseDouble(datoUsuario);
        }
        if (validaDato == 0) {
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_wait));
        } else {
            if (validaDato >= tolerancia(datoCalculado, 2) && validaDato <= tolerancia(datoCalculado, 1)) {
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_ok));
            } else {
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_answer_no));
            }
        }
    }

    public double tolerancia(double valor, int tolerancia) {
        int valorTolerancia = 2;
        if (tolerancia == 1) {
            valor = valor + (valor * valorTolerancia) / 100;
        } else {
            valor = valor - (valor * valorTolerancia) / 100;
        }

        return valor;
    }

    public void controlBack(View view) {
        inicializarDatos();
        pantallaPrincipal();
    }

    public void intentarConexion(View view) {
        connectWebSocket();
    }

    public void pantallaPrincipal() {

        //Layouts que se muestran
        llyLogo.setVisibility(View.VISIBLE);
        rlyModulosControl.setVisibility(View.VISIBLE);

        //Layouts que se ocultan
        rlyConexion.setVisibility(View.GONE);
        rlyModulosControlBack.setVisibility(View.GONE);
        rlyModulosPlanoInclinado.setVisibility(View.GONE);
        rlyModulosPenduloSimple.setVisibility(View.GONE);
        rlyModulosMovimientoCircular.setVisibility(View.GONE);

        txtConexion.setText(getResources().getString(R.string.datosRecibidos_hint_estadoConectado));

    }

    public void conexionCerrada() {

        //Layouts que se muestran
        llyLogo.setVisibility(View.VISIBLE);
        rlyConexion.setVisibility(View.VISIBLE);

        //Layouts que se ocultan
        rlyModulosControl.setVisibility(View.GONE);
        rlyModulosControlBack.setVisibility(View.GONE);
        rlyModulosPlanoInclinado.setVisibility(View.GONE);
        rlyModulosPenduloSimple.setVisibility(View.GONE);
        rlyModulosMovimientoCircular.setVisibility(View.GONE);

        txtConexion.setText(getResources().getString(R.string.datosRecibidos_hint_estadoDesconectado));
    }

    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    public void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.4.1:81");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                // mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                thread(1, null);
            }

            @Override
            public void onMessage(String message) {
                Log.i("Websocket", "Message " + message);
                try {

                    JSONObject jsonObject = new JSONObject(message);
                    messageJson = jsonObject;

                    Log.i("estado", mWebSocketClient.getReadyState().toString());

                    if (jsonObject.getString("estado").trim().equals("conectado")) {
                        if (jsonObject.getString("modulo").trim().equals("")) {
                            messageInicial = messageJson;
                            thread(2, null);
                        } else {
                            thread(5, message);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                thread(3, null);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
                thread(4, e.getMessage());
            }

            public void thread(final int event, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (event) {
                            case 1:
                                break;
                            case 2:
                                pantallaPrincipal();
                                break;
                            case 3:
                                conexionCerrada();
                                break;
                            case 4:
                                conexionCerrada();
                                break;
                            case 5:
                                break;
                        }
                    }
                });
            }

        };
        mWebSocketClient.connect();
    }
}
