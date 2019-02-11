package me.parzibyte.ejemplodepermisos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Banderas que indicarán si tenemos permisos
    private boolean tienePermisoCamara = false,
            tienePermisoAlmacenamiento = false;

    // Código de permiso, defínelo tú mismo
    private static final int CODIGO_PERMISOS_CAMARA = 1,
            CODIGO_PERMISOS_ALMACENAMIENTO = 2;

    private void verificarYPedirPermisosDeCamara() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoDeCamaraConcedido();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }

    private void verificarYPedirPermisosDeAlmacenamiento() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoDeAlmacenamientoConcedido();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_ALMACENAMIENTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_CAMARA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeCamaraConcedido();
                } else {
                    permisoDeCamaraDenegado();
                }
                break;

            case CODIGO_PERMISOS_ALMACENAMIENTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeAlmacenamientoConcedido();
                } else {
                    permisoDeAlmacenamientoDenegado();
                }
                break;

            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...

        }
    }

    private void permisoDeAlmacenamientoConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el permiso se concediera. Por
        // ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
        Toast.makeText(MainActivity.this, "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoAlmacenamiento = true;
    }

    private void permisoDeAlmacenamientoDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(MainActivity.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();
    }

    private void permisoDeCamaraConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el acceso a la cámara se condeciera
        // Por ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
        Toast.makeText(MainActivity.this, "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoCamara = true;
    }

    private void permisoDeCamaraDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(MainActivity.this, "El permiso para la cámara está denegado", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Algunos botones que, al ser tocados, van a pedir los permisos
        Button btnPermisoCamara = findViewById(R.id.btnPermisoCamara),
                btnPermisoAlmacenamiento = findViewById(R.id.btnPermisoAlmacenamiento);
        btnPermisoCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarYPedirPermisosDeCamara();
            }
        });

        btnPermisoAlmacenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarYPedirPermisosDeAlmacenamiento();
            }
        });
    }
}
