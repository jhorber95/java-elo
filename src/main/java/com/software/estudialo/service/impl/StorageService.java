package com.software.estudialo.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.software.estudialo.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.software.estudialo.dao.EventoDao;
import com.software.estudialo.dao.InstitucionDao;
import com.software.estudialo.dao.OfertaDao;
import com.software.estudialo.dao.PublicidadDao;
import com.software.estudialo.dao.UsuarioDao;
import com.software.estudialo.exception.DAOException;
import com.software.estudialo.exception.ObjectNotFoundException;
import com.software.estudialo.exception.ValueNotPermittedException;
import com.software.estudialo.util.Constants;

@Service("storageService")
public class StorageService {

    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    InstitucionDao institucionDao;

    @Autowired
    EventoDao eventoDao;

    @Autowired
    OfertaDao ofertaDao;

    @Autowired
    PublicidadDao publicidadDao;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get(Constants.ROOT_LOCATION);
    private final Path usuarioFolderLocation = Paths.get(Constants.USUARIO_LOCATION);
    private final Path institucionFolderLocation = Paths.get(Constants.INSTITUCION_LOCATION);
    private final Path ofertaFolderLocation = Paths.get(Constants.OFERTA_LOCATION);
    private final Path eventoFolderLocation = Paths.get(Constants.EVENTO_LOCATION);
    private final Path noticiaFolderLocation = Paths.get(Constants.NEWS_LOCATION);
    private final Path testFolderLocation = Paths.get(Constants.TEST_LOCATION);
    private final Path publicidadFolderLocation = Paths.get(Constants.PUBLICIDAD_LOCATION);

    public void cambiarImagenUsuario(MultipartFile file, String Stringlocation, int idUsuario) {

        String type = file.getContentType();
        String extension = "";

        if (type.equalsIgnoreCase("image/jpeg")) {
            extension = ".jpg";
        } else if (type.equalsIgnoreCase("image/png")) {
            extension = ".png";
        } else {
            throw new ValueNotPermittedException("No se permiten imagenes de ese tipo, solo de tipo png y jpg");
        }

        if (Stringlocation.equals(Constants.USUARIO_LOCATION)) {

            Boolean existeUsuario = usuarioDao.buscarUsuario(idUsuario);

            if (!existeUsuario) {
                throw new ObjectNotFoundException("El id de usuario no existe!");
            } else {
                try {

                    // Eliminar todas las imagenes que tengan el nombre del id
                    // enviado
                    File op1 = new File(Constants.USUARIO_LOCATION + "//" + String.valueOf(idUsuario) + ".jpg");

                    File op2 = new File(Constants.USUARIO_LOCATION + "//" + String.valueOf(idUsuario) + ".png");

                    File op3 = new File(Constants.USUARIO_LOCATION + "//" + String.valueOf(idUsuario) + ".JPG");

                    deleteImage(op1);
                    deleteImage(op2);
                    deleteImage(op3);

                    CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                    String newFileName = String.valueOf(idUsuario) + extension;

                    Files.copy(file.getInputStream(), this.usuarioFolderLocation.resolve(newFileName), options);

                    // Cambiar en la db
                    Boolean cambiado = usuarioDao.modificarImagenUsuario(idUsuario, newFileName);

                    if (!cambiado) {
                        throw new DAOException("No se pudo cambiar el nombre en la db");
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {
            throw new ObjectNotFoundException("La ruta especificada no corresponde");
        }

    }

    public void cambiarImagenInstitucion(MultipartFile file, String Stringlocation, int idInstitucion) {

        String type = file.getContentType();
        String extension = "";

        if (type.equalsIgnoreCase("image/jpeg")) {
            extension = ".jpg";
        } else if (type.equalsIgnoreCase("image/png")) {
            extension = ".png";
        } else {
            throw new ValueNotPermittedException("No se permiten imagenes de ese tipo, solo de tipo png y jpg");
        }

        if (Stringlocation.equals(Constants.INSTITUCION_LOCATION)) {

            Boolean existeInstitucion = institucionDao.buscarInstitucion(idInstitucion);

            if (!existeInstitucion) {
                throw new ObjectNotFoundException("El id de institucion no existe!");
            } else {
                try {

                    // Eliminar todas las imagenes que tengan el nombre del id
                    // enviado
                    File op1 = new File(Constants.INSTITUCION_LOCATION + "//" + String.valueOf(idInstitucion) + ".jpg");

                    File op2 = new File(Constants.INSTITUCION_LOCATION + "//" + String.valueOf(idInstitucion) + ".png");

                    File op3 = new File(Constants.INSTITUCION_LOCATION + "//" + String.valueOf(idInstitucion) + ".JPG");

                    deleteImage(op1);
                    deleteImage(op2);
                    deleteImage(op3);

                    CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                    String newFileName = String.valueOf(idInstitucion) + extension;

                    Files.copy(file.getInputStream(), this.institucionFolderLocation.resolve(newFileName), options);

                    // Cambiar en la db
                    Boolean cambiado = institucionDao.modificarImagenInstitucion(idInstitucion, newFileName);

                    if (!cambiado) {
                        throw new DAOException("No se pudo cambiar el nombre en la db");
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {
            throw new ObjectNotFoundException("La ruta especificada no corresponde");
        }

    }

    public void cambiarImagenEvento(MultipartFile file, String Stringlocation, int idEvento) {

        String type = file.getContentType();
        String extension = "";

        if (type.equalsIgnoreCase("image/jpeg")) {
            extension = ".jpg";
        } else if (type.equalsIgnoreCase("image/png")) {
            extension = ".png";
        } else {
            throw new ValueNotPermittedException("No se permiten imagenes de ese tipo, solo de tipo png y jpg");
        }

        if (Stringlocation.equals(Constants.EVENTO_LOCATION)) {

            Boolean existeEvento = eventoDao.buscarEvento(idEvento);

            if (!existeEvento) {
                throw new ObjectNotFoundException("El id de evento no existe!");
            } else {
                try {

                    // Eliminar todas las imagenes que tengan el nombre del id
                    // enviado
                    File op1 = new File(Constants.EVENTO_LOCATION + "//" + String.valueOf(idEvento) + ".jpg");

                    File op2 = new File(Constants.EVENTO_LOCATION + "//" + String.valueOf(idEvento) + ".png");

                    File op3 = new File(Constants.EVENTO_LOCATION + "//" + String.valueOf(idEvento) + ".JPG");

                    deleteImage(op1);
                    deleteImage(op2);
                    deleteImage(op3);

                    CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                    String newFileName = String.valueOf(idEvento) + extension;

                    Files.copy(file.getInputStream(), this.eventoFolderLocation.resolve(newFileName), options);

                    // Cambiar en la db
                    Boolean cambiado = eventoDao.modificarImagenEvento(idEvento, newFileName);

                    if (!cambiado) {
                        throw new DAOException("No se pudo cambiar el nombre en la db");
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {
            throw new ObjectNotFoundException("La ruta especificada no corresponde");
        }

    }

    public void cambiarImagenOferta(MultipartFile file, String Stringlocation, int idOferta) {

        String type = file.getContentType();
        String extension = "";

        if (type.equalsIgnoreCase("image/jpeg")) {
            extension = ".jpg";
        } else if (type.equalsIgnoreCase("image/png")) {
            extension = ".png";
        } else {
            throw new ValueNotPermittedException("No se permiten imagenes de ese tipo, solo de tipo png y jpg");
        }

        if (Stringlocation.equals(Constants.OFERTA_LOCATION)) {

            Boolean existeOferta = ofertaDao.buscarOferta(idOferta);

            if (!existeOferta) {
                throw new ObjectNotFoundException("El id de evento no existe!");
            } else {
                try {

                    // Eliminar todas las imagenes que tengan el nombre del id
                    // enviado
                    File op1 = new File(Constants.OFERTA_LOCATION + "//" + String.valueOf(idOferta) + ".jpg");

                    File op2 = new File(Constants.OFERTA_LOCATION + "//" + String.valueOf(idOferta) + ".png");

                    File op3 = new File(Constants.OFERTA_LOCATION + "//" + String.valueOf(idOferta) + ".JPG");

                    deleteImage(op1);
                    deleteImage(op2);
                    deleteImage(op3);

                    CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                    String newFileName = String.valueOf(idOferta) + extension;

                    Files.copy(file.getInputStream(), this.ofertaFolderLocation.resolve(newFileName), options);

                    // Cambiar en la db
                    Boolean cambiado = ofertaDao.modificarImagenOferta(idOferta, newFileName);

                    if (!cambiado) {
                        throw new DAOException("No se pudo cambiar el nombre en la db");
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {
            throw new ObjectNotFoundException("La ruta especificada no corresponde");
        }

    }

    public void cambiarImagenBanner(MultipartFile file, String Stringlocation, int idBanner, String url) {

        String type = file.getContentType();
        String extension = "";

        if (type.equalsIgnoreCase("image/jpeg")) {
            extension = ".jpg";
        } else if (type.equalsIgnoreCase("image/png")) {
            extension = ".png";
        } else if (type.equalsIgnoreCase("image/gif")) {
            extension = ".gif";
        } else {
            throw new ValueNotPermittedException("No se permiten imagenes de ese tipo, solo de tipo png, jpg y gif");
        }

        if (Stringlocation.equals(Constants.PUBLICIDAD_LOCATION)) {

            try {

                // Eliminar todas las imagenes que tengan el nombre del id
                // enviado
                File op1 = new File(Constants.PUBLICIDAD_LOCATION + "//" + String.valueOf(idBanner) + ".jpg");

                File op2 = new File(Constants.PUBLICIDAD_LOCATION + "//" + String.valueOf(idBanner) + ".png");

                File op3 = new File(Constants.PUBLICIDAD_LOCATION + "//" + String.valueOf(idBanner) + ".JPG");

                File op4 = new File(Constants.PUBLICIDAD_LOCATION + "//" + String.valueOf(idBanner) + ".gif");

                deleteImage(op1);
                deleteImage(op2);
                deleteImage(op3);
                deleteImage(op4);

                CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                String newFileName = String.valueOf(idBanner) + extension;

                Files.copy(file.getInputStream(), this.publicidadFolderLocation.resolve(newFileName), options);

                // Cambiar en la db
                Boolean cambiado = publicidadDao.modificarPublicidad(idBanner, newFileName, url);

                if (!cambiado) {
                    throw new DAOException("No se pudo cambiar el nombre en la db");
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            throw new ObjectNotFoundException("La ruta especificada no corresponde");
        }

    }

    private void deleteImage(File file) {
        boolean success = false;
        // if (file.isDirectory()) {
        // for (File deleteMe : file.listFiles()) {
        // // recursive delete
        // deleteImage(deleteMe);
        // }
        // }

        if (file.exists()) {
            success = file.delete();
            if (success) {
                logger.debug("Se elimino = " + file.getAbsoluteFile() + " Deleted");
            } else {
                logger.debug("Algo sucedio, no elimino = " + file.getAbsoluteFile() + " Deletion failed!!!");
            }
        }

    }

    public Resource loadFile(String filename, String Stringlocation) {
        try {

            Path file = null;

            if (Stringlocation.equals(Constants.USUARIO_LOCATION)) {
                file = usuarioFolderLocation.resolve(filename);
            } else if (Stringlocation.equals(Constants.INSTITUCION_LOCATION)) {
                file = institucionFolderLocation.resolve(filename);
            } else if (Stringlocation.equals(Constants.OFERTA_LOCATION)) {
                file = ofertaFolderLocation.resolve(filename);
            } else if (Stringlocation.equals(Constants.EVENTO_LOCATION)) {
                file = eventoFolderLocation.resolve(filename);
            } else if (Stringlocation.equals(Constants.TEST_LOCATION)) {
                file = testFolderLocation.resolve(filename);
            } else if (Stringlocation.equals(Constants.PUBLICIDAD_LOCATION)) {
                file = publicidadFolderLocation.resolve(filename);
            } else if (Stringlocation.equals(Constants.NEWS_LOCATION)) {
                file = noticiaFolderLocation.resolve(filename);
            }  else {
                throw new ObjectNotFoundException("La ruta especificada no corresponde");
            }

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ObjectNotFoundException("La imagen no existe o esta en malas condiciones!");
                // throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            // throw new RuntimeException("FAIL!");
            throw new ObjectNotFoundException("Problema con la URL");
        }
    }

    public void init() {

        File rootFolder = new File("C://imagenes-estudialo");
        File usuarioFolder = new File("C://imagenes-estudialo//usuario");
        File institucionFolder = new File("C://imagenes-estudialo//institucion");
        File ofertaFolder = new File("C://imagenes-estudialo//oferta");
        File eventoFolder = new File("C://imagenes-estudialo//evento");

        if (!rootFolder.exists()) {
            try {
                logger.debug("Creando Carpeta ROOT");
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear la carpeta ROOT!");
            }
        }

        if (!usuarioFolder.exists()) {
            try {
                logger.debug("Creando Carpeta USUARIOS");
                Files.createDirectory(usuarioFolderLocation);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear la carpeta usuario!");
            }
        }

        if (!institucionFolder.exists()) {
            try {
                logger.debug("Creando Carpeta INSTITUCIONES");
                Files.createDirectory(institucionFolderLocation);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear la carpeta institucion");
            }
        }

        if (!ofertaFolder.exists()) {
            try {
                logger.debug("Creando Carpeta OFERTAS");
                Files.createDirectory(ofertaFolderLocation);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear la carpeta oferta");
            }
        }

        if (!eventoFolder.exists()) {
            try {
                logger.debug("Creando Carpeta EVENTOS");
                Files.createDirectory(eventoFolderLocation);
            } catch (IOException e) {
                throw new RuntimeException("No se pudo crear la carpeta evento");
            }
        }

    }

    public String uploadNewsImage(MultipartFile file, String Stringlocation) {

        String type = file.getContentType();
        String extension = "";

        if (type.equalsIgnoreCase("image/jpeg")) {
            extension = ".jpeg";
        } else if (type.equalsIgnoreCase("image/png")) {
            extension = ".png";
        } else if (type.equalsIgnoreCase("image/jpg")) {
            extension = ".jpg";
        } else {
            throw new ValueNotPermittedException("No se permiten imagenes de ese tipo, solo de tipo png y jpg");
        }

        CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
        String newFileName = RandomUtil.generateString() + extension;

        try {
            Files.copy(file.getInputStream(), this.noticiaFolderLocation.resolve(newFileName), options);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;

    }
}