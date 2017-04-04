package com.siestasystemheadpod.headpodv10.adicionales.foto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.SharedPreferSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//import android.graphics.BitmapFactory;
//import java.io.FileInputStream;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;

/**
 * Created by Michael on 19/01/2017.
 *
 * Esta clase sirve para mandar la tarea de tomar una foto con la camara desde un fragmento.
 *
 * IMPORTANTE
 * ***********
 * Es necesario que el fragmento que crea una instancia de esta clase  sobreescriba el metodo
 *   public void onActivityResult(int requestCode, int resultCode, Intent data)
 */


/*
        //Bq aquaris camara trasera
        //******************************************
        //targetW: 120
        //targetH: 160
        //photoW: 1920
        //photoH: 2560
        //*****************************************
        //camara frontal Huawei
        //targetW: 120//98
        //targetH: 160//78
        //photoW: 720
        //photoH: 1280

 */
//http://stackoverflow.com/questions/14266225/how-to-call-startactivityforresult-from-a-non-activity-class-to-get-the-resuts
public class TakePicturePaciente {


    private Fragment fragment;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private String mCurrentPhotoPath;//ruta
    private String imageFileName;//Nombre del archivo temporal
    private String directorio; //Correo de usuario
    private SharedPreferSession sharedPreferSession;



    public TakePicturePaciente(Fragment fragment)
    {
        this.fragment=fragment;
        sharedPreferSession=new SharedPreferSession(fragment.getContext());
        directorio =sharedPreferSession.getUserEmail();



    }






    public String getmCurrentPhotoPath()
    {
        return mCurrentPhotoPath;
    }

    public void dispatchTakePictureIntentPerfilTerapeuta() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImagePerfilTerapeuta(directorio);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("error_TakePicture","error en createImagePerfilTerapeuta_ "+ex.toString());
                Toast.makeText(fragment.getContext(),"Error"+ex.toString(),Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // Uri photoURI = FileProvider.getUriForFile(getContext(),
                //         "com.siestasystemheadpod.headpodv10",
                //    photoFile);
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                Uri photoURI;

                //"com.siestasystemheadpod.headpodv10"-->getPackageName
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Build.VERSION.SDK_INT >= 21
                    photoURI = FileProvider.getUriForFile(fragment.getContext(), fragment.getActivity().getPackageName(), photoFile);//api >=24 nougat//Cambiar a getContext()
                    //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                }
                else
                {
                    photoURI = Uri.fromFile(photoFile);
                    // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //Mandamos el resultado al fragmento. Ver su metodo onActivityResult
                fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }




    public void dispatchTakePictureIntentPaciente() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImagePaciente(directorio);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("error_TakePicture","error en createImagePaciente_ "+ex.toString());
                Toast.makeText(fragment.getContext(),"Error"+ex.toString(),Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // Uri photoURI = FileProvider.getUriForFile(getContext(),
                //         "com.siestasystemheadpod.headpodv10",
                //    photoFile);
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                Uri photoURI;

                //"com.siestasystemheadpod.headpodv10"-->getPackageName
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Build.VERSION.SDK_INT >= 21
                    photoURI = FileProvider.getUriForFile(fragment.getContext(), fragment.getActivity().getPackageName(), photoFile);//api >=24 nougat//Cambiar a getContext()
                    //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                }
                else
                {
                    photoURI = Uri.fromFile(photoFile);
                    // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //Mandamos el resultado al fragmento. Ver su metodo onActivityResult
                fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }




    public void borrarImagePaciente(String directorio)
    {
        try {

            if(imageFileName!=null) {
                File storageDir = fragment.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + directorio + "/");
                File photoFile = new File(storageDir, imageFileName);
                boolean bool = photoFile.delete();
                if (bool) {
                    Log.d("Imagen", "borrada");
                } else {
                    Log.d("imagen", "no borrada");
                }
            }
        } catch (Exception ex) {
            // Error occurred while creating the File
            Log.e("error_TakePicture","error borrarImagePaciente_ "+ex.toString());
            Toast.makeText(fragment.getContext(),"Error_takePicture borrarImagePactiente"+ex.toString(),Toast.LENGTH_SHORT).show();
        }

    }










    private File createImagePerfilTerapeuta(String directorio) throws IOException //Nombre del correo
    {
        String imageFileName = "JPEG_PERFIL.jpg";
        File storageDir = fragment.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+directorio);
        File image = new File(storageDir, imageFileName);

        //Log.d("img_lenght",String.valueOf(image.length()));//984065 //Este da el espacio en bytes
        //boolean bool= image.createNewFile();
        //Log.d("img","la imagen fue creada "+String.valueOf(bool));

        /*  //Esta es la otra forma de crear una imagen:
        File image = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",         // suffix
                     storageDir     // directory
         );
        */
        //Asignamos la ruta:
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private File createImagePaciente(String directorio) throws IOException //Nombre del correo
    {

        /*
          //obtener fecha actual
        Calendar c = Calendar.getInstance();
        int ano_actual = c.get(Calendar.YEAR);
        int mes_actual = c.get(Calendar.MONTH);
        int dia_actual = c.get(Calendar.DAY_OF_MONTH);
         */

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        int hora24h= calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int min=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);



        //new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String timeStamp = obtainDateHour(dia,mes-1,ano,hora24h,min,second);
        imageFileName = "Paciente_" + timeStamp+"_TEMP.jpg";


        File storageDir = fragment.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+directorio+"/");


        File image = new File(storageDir, imageFileName);
        //Asignamos la ruta:
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;

    }



    private String obtainDateHour(int dia, int mes, int ano, int hora, int min , int s)
    {
        return String.valueOf(dia)+"-"+String.valueOf(mes)+"-"+String.valueOf(ano)+"_"+String.valueOf(hora)+"-"+String.valueOf(min)+"-"+String.valueOf(s);
    }



    //Foto caramar frontal del huawei
        // Resolución: (720 x 1280)
        //Tamaño: 248.445 Byres (242.62 KB)

        //tamano: 228.257 bytes (22.91 kb)

    //resultado
        // Resolucion 256 x 512(deseado) --> 256 x 455 (final)
        //Tamaño: 16.469 Bytes (16.08 KB)

        //tamano: 14.209 (13.88kb)

    //Método para reducir la calidad de la imagen antes de subir la foto al servidor
    //how-to-reduce-an-image-file-size-before-uploading-to-a-server
    //NOTA: Este coge la foto que ya exista de antes en la ruta que se pasa, es decir, la imagen
    //tiene que existir.
    public String decodeFile(String path,int DESIREDWIDTH, int DESIREDHEIGHT,String ficheroName) {
        String strMyImagePath = null;
        //Bitmap scaledBitmap = null;
        Bitmap scaledBitmap;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            //String extr = Environment.getExternalStorageDirectory().toString();
            //File mFolder = new File(extr + "/TMMFOLDER");

            File mFolder = fragment.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/"+directorio);

            if (mFolder!=null && !mFolder.exists()) {
                boolean mkdir=mFolder.mkdir();
                if(mkdir)
                    Log.e("Directorio","creado");
            }

            //String s = "tmp.png";
            //String s = "JPEG_PERFIL.jpg";

            if(mFolder!=null) {
                File f = new File(mFolder.getAbsolutePath(), ficheroName);
                strMyImagePath = f.getAbsolutePath();
                //  FileOutputStream fos = null;
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(f);
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("ErrorTakePictureFile", e.toString());
                } catch (Exception e) {
                    //e.printStackTrace()
                    Log.e("ErrorTakePictureExcep", e.toString());
                }

                scaledBitmap.recycle();
            }
        } catch (Throwable e) {
            Log.e("ErrorTakePictureThrowab",e.toString());
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }


    public String getImageFileName()
    {
        return imageFileName;
    }

/*
    private File createImageFile() throws IOException {
        // Create an image file name

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        int hora24h= calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int min=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);


        //File dir = new File (root.getAbsolutePath() + "/Headpod");

        //new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String timeStamp = obtainDateHour(dia,mes-1,ano,hora24h,min,second);
        String imageFileName = "JPEG_" + timeStamp + "_";

        //StorageDir --> Environment.DIRECTORY_PICTURES
        //Log.d("storageDir",storageDir.getAbsolutePath());//da como resultado la linea de abajo.
        // /storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures

        //No hace falta crear el directorio, al añadirlo en la ruta se crea solo.
        File storageDir = fragment.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/usuario2");
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
        //Nota: Si surge un error por crear el directorio ver
        //*****************************************************************************************
        //https://developer.android.com/training/permissions/requesting.html#explain
        //https://docs.google.com/document/d/1Nh_vAYJW-5Q9vghKKLxaMCUcRMd_e3_SU3wlmeZT5Jc/edit

        //crearDirectorio(directorio);
        //******************************************************************************************
        //Uri path = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.monigote_verde_perfil);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        ///storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures/usuario2/JPEG_18-12017_91328_1586187593.jpg

        Log.d("mCurrentPhotoPath_act",mCurrentPhotoPath);
        return image;
    }
*/

    /*
    private String obtainDateHour(int dia, int mes, int ano, int hora, int min , int s)
    {
        return String.valueOf(dia)+String.valueOf(mes)+String.valueOf(ano)+"_"+String.valueOf(hora)+String.valueOf(min)+String.valueOf(s);
    }*/





    //Estge metodo en principio no lo usamos pero lo dejo por si hace falta si surge algun problema
    //mas adelante.
    //*******************************************************************************************************************
    //*******************************************************************************************************************

   /*
    public void crearDirectorio(String directorio)
    {
        File root3= android.os.Environment.getExternalStorageDirectory();
        String ruta =root3.getAbsolutePath()+"/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures/"+directorio;
        File dir = new File (ruta);
        if(!dir.exists()) {
            boolean aaux= dir.mkdirs();
            Log.d("DIRECTORIO:",String.valueOf(aaux));
        }
        else
        {
            Log.d("DIRECTORIO:","ya existe");
        }
    }
    */
    //*******************************************************************************************************************
    //*******************************************************************************************************************



}



/*
    private void setPicOriginal() {
        // Get the dimensions of the View
       // int targetW = imgPerfil.getWidth();//98
        //int targetH = imgPerfil.getHeight();//78

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;// 1920
        int photoH = bmOptions.outHeight;//2560

        // Determine how much to scale down the image
        //int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        //Log.d("scaleFactor",String.valueOf(scaleFactor));
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        //bmOptions.inSampleSize = scaleFactor;
        // bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        //Bitmap imagenFinal = Bitmap.createScaledBitmap(bitmap, 98,133 , false);
        //imgPerfil.setImageBitmap(bitmap);
    }

*/


//https://github.com/mattspitz/android-playground/blob/master/PhotoIntentActivity/src/com/example/android/photobyintent/PhotoIntentActivity.java
//Esto no funciona, no añade a la galeria.
    /*
    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Log.d("mcurrentPath-gallery",mCurrentPhotoPath);
        //File f = new File("file:"+mCurrentPhotoPath);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        // Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
        //getActivity().sendBroadcast(mediaScanIntent);
    }
*/


/*
    public void modificarImagenPerfil()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



        if (takePictureIntent.resolveActivity(
                getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }



    }
    */





/*


    private File createImageFile() throws IOException {
        // Create an image file name

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        int hora24h= calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int min=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);


        //File dir = new File (root.getAbsolutePath() + "/Headpod");

        //new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String timeStamp = obtainDateHour(dia,mes-1,ano,hora24h,min,second);
        String imageFileName = "JPEG_" + timeStamp + "_";

        //StorageDir --> Environment.DIRECTORY_PICTURES
        //Log.d("storageDir",storageDir.getAbsolutePath());//da como resultado la linea de abajo.
        // /storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures

        //No hace falta crear el directorio, al añadirlo en la ruta se crea solo.
        File storageDir = fragment.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/usuario2");
        File image = File.createTempFile(
                imageFileName,  // prefix
".jpg",         // suffix
        storageDir      // directory
        );
        //Nota: Si surge un error por crear el directorio ver
        //*****************************************************************************************
        //https://developer.android.com/training/permissions/requesting.html#explain
        //https://docs.google.com/document/d/1Nh_vAYJW-5Q9vghKKLxaMCUcRMd_e3_SU3wlmeZT5Jc/edit

        //crearDirectorio(directorio);
        //******************************************************************************************
        //Uri path = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.monigote_verde_perfil);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        ///storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures/usuario2/JPEG_18-12017_91328_1586187593.jpg

        Log.d("mCurrentPhotoPath_act",mCurrentPhotoPath);
        return image;
        }

private String obtainDateHour(int dia, int mes, int ano, int hora, int min , int s)
        {
        return String.valueOf(dia)+String.valueOf(mes)+String.valueOf(ano)+"_"+String.valueOf(hora)+String.valueOf(min)+String.valueOf(s);
        }



 */

/*//obtener tamaño de la memoria
//**************************************************************************************************
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        String size =Formatter.formatFileSize(getContext(), availableBlocks * blockSize);

        Log.d("tamano_memoria",size);
//**************************************************************************************************
 */