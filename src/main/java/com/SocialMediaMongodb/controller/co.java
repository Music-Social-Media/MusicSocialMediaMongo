//package com.SocialMediaMongodb.controller;
//
//import com.SocialMediaMongodb.model.Album;
//import com.SocialMediaMongodb.model.Artist;
//import com.SocialMediaMongodb.model.Media;
//import com.SocialMediaMongodb.model.User;
//import com.SocialMediaMongodb.service.AlbumService;
//import com.SocialMediaMongodb.service.ArtistService;
//import com.SocialMediaMongodb.service.MediaService;
//import com.SocialMediaMongodb.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//public class co {
//
//    private static final String BASE_DIR = "src/main/resources/static/music-files/";
//
//    @Autowired
//    private MediaService mediaService;
//    private UserService userService;
//    private ArtistService artistService;
//    private AlbumService albumService;
//
//    //*************************user*******************************//
//
//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/signup")
//    public String signUp() {
//        return "signup";
//    }
//
//    @RequestMapping(value = "/user/signup")
//    public String registerUser(HttpServletRequest request, @RequestParam("username") String username,
//                               @RequestParam("email") String email, @RequestParam("password") String password) {
//        User isExitUser = userService.getUserByEmail(email);
//        System.out.println("iiiiii" + isExitUser.toString());
//        if (isExitUser == null) {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setPassword(password);
//            newUser.setUsername(username);
//            userService.addUser(newUser);
//
//            HttpSession session = request.getSession();
//            session.setAttribute("userID", userService.getUserByEmail(email).getUserID());
//
//            return "index";
//        } else {
//            System.out.println("User Already exists!");
//            return "signup";
//        }
//    }
//
//    @RequestMapping(value = "/user/login")
//    public String loginUser(HttpServletRequest request) {
//        User isExitUser = userService.getUserByEmail(request.getParameter("email"));
//        if (isExitUser != null && isExitUser.getPassword().equals(request.getParameter("password"))) {
//            HttpSession session = request.getSession();
//            session.setAttribute("userID", isExitUser.getUserID());
//            return "index";
//        } else
//            return "login";
//    }
//
//
//    @GetMapping("/profile")
//    public ModelAndView profile(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        System.out.println(session.getAttribute("userID"));
//        User user = userService.getUser((String) session.getAttribute("userID"));
//        System.out.println(user.toString());
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("user", user);
//        model.setViewName("profile");
//
//        return model;
//    }
//
//    @GetMapping("/user/changePassword")
//    public ModelAndView userEditPassword(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        User user = userService.getUser((String) session.getAttribute("userID"));
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("user", user);
//        model.setViewName("changePassword");
//        return model;
//    }
//
//    @RequestMapping("/user/edit")
//    public String updateUser(@RequestParam("id") String id, @RequestParam("firstName") String firstName,
//                             @RequestParam("lastName") String lastName) {
//        User user = userService.getUser(id);
//        if (user != null) {
//            user.setFirstname(firstName);
//            user.setLastname(lastName);
////            user.setBiography(biography);
//            userService.updateUser(user);
//        }
//        return "profile";
//    }
//
//    @RequestMapping("/changePassword")
//    public String changePassword(HttpServletRequest request, @RequestParam("currentPassword") String currentPassword, @RequestParam("password") String password,
//                                 @RequestParam("repeatNewPassword") String repeatNewPassword) {
//        HttpSession session = request.getSession();
//        User user = userService.getUser((String) session.getAttribute("userID"));
//        System.out.println(user.toString());
//        if (user != null && user.getPassword().equals(currentPassword) && password.equals(repeatNewPassword)) {
//            user.setPassword(password);
//            userService.updateUser(user);
//        }
//        return "changePassword";
//    }
//
//    @RequestMapping("/user/getAll")
//    public String getAllUsers() {
//        System.out.println(userService.getAllUser());
//        return "index";
//    }
//
//    //*************************artist*******************************//
//
//    private static final String ALBUM_IMG_DIR = "src/main/resources/uploads/";
//
//    @PostMapping("/artist/add")
//    public ResponseEntity addArtist(@RequestParam("file") MultipartFile file,
//                                    @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
//                                    @RequestParam("biography") String biography, @RequestParam("birthDate") String birthDate) throws IOException {
//        Path path = Paths.get(ALBUM_IMG_DIR + file.getOriginalFilename());
//        Files.write(path, file.getBytes());
//
//        if (artistService.findArtistNOTDuplicate(firstName, lastName, birthDate)) {
//            Artist artist = new Artist(firstName, lastName, ALBUM_IMG_DIR + file.getOriginalFilename(), biography, birthDate);
//            artistService.addOrUpdateArtist(artist);
//            return new ResponseEntity<>(artist, HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    @PostMapping("/artist/update/{id}")
//    public ResponseEntity UpdateArtist(@PathVariable("id") String id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
//                                       @RequestParam("biography") String biography, @RequestParam("birthDate") String birthDate) {
//        Artist artist = artistService.getArtist(id);
//        artist.setFirstname(firstName);
//        artist.setLastname(lastName);
//        artist.setBiography(biography);
//        artist.setBirthdate(birthDate);
//        boolean response = artistService.addOrUpdateArtist(artist);
//        if (response)
//            return new ResponseEntity<>(artist, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/artist/delete/{id}")
//    public ResponseEntity deleteArtist(@PathVariable("id") String id) {
//        boolean response = artistService.deleteArtist(id);
//        if (response)
//            return new ResponseEntity<>(HttpStatus.OK);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(path = "/artists")
//    public @ResponseBody
//    ModelAndView getAllArtists() {
//        ModelAndView model = new ModelAndView();
//        List<Artist> artists = artistService.getAllArtist();
//
//        model.addObject("artists", artists);
//        model.setViewName("artists");
//        return model;
//    }
//
//    @GetMapping(path = "/artist/get/{id}")
//    public ModelAndView getArtist(@PathVariable("id") String id) {
//        ModelAndView model = new ModelAndView();
//        Artist artist = artistService.getArtist(id);
//
//        model.addObject("artist", artist);
//        model.setViewName("artist");
//
//        return model;
//    }
//
//    //*************************album*******************************//
//
//    @PostMapping("/album/add")
//    public ResponseEntity addAlbum(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("genre") String genre) throws IOException {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//
//        Path path = Paths.get(ALBUM_IMG_DIR + file.getOriginalFilename());
//        Files.write(path, file.getBytes());
//
//        Album album = new Album(name, formatter.format(date), 0, genre, ALBUM_IMG_DIR + file.getOriginalFilename());
//        albumService.addOrUpdateAlbum(album);
//
//        return new ResponseEntity<>(album, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/album/delete/{id}")
//    public ResponseEntity deleteAlbum(@PathVariable("id") String id) {
//        boolean flag = albumService.deleteAlbum(id);
//        if (flag)
//            return new ResponseEntity<>(HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("album/update/{id}")
//    public ResponseEntity updateAlbum(@PathVariable("id") String id, @RequestParam("albumName") String albumName,
//                                      @RequestParam("genre") String genre, @RequestParam("score") int score,
//                                      @RequestParam("publishDate") String publishDate) {
//        Album album = albumService.getAlbum(id);
//        if (album != null) {
//            album.setName(albumName);
//            album.setGenre(genre);
//            album.setScore(score);
//            album.setPublishDate(publishDate);
//            albumService.addOrUpdateAlbum(album);
//            return new ResponseEntity(album, HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/album/get/{id}")
//    public ModelAndView getAlbum(@PathVariable("id") String id) {
//        ModelAndView model = new ModelAndView();
//        Album album = albumService.getAlbum(id);
//        System.out.println("---" + album.toString());
//        System.out.println("id:::" + id);
//
//        List<Media> media = mediaService.getMediaByAlbumID(id);
//        System.out.println(media.toString());
//        model.addObject("album", album);
//        if (media.size() > 0)
//            model.addObject("media", media.get(0));
//        model.addObject("medias", media);
//        model.setViewName("album");
//
//        return model;
//    }
//
//    @GetMapping("/albums")
//    public ModelAndView getAllAlbum() {
//        ModelAndView model = new ModelAndView();
//        List<Album> albums = albumService.getAllAlbum();
//
//        model.addObject("albums", albums);
//        model.setViewName("albums");
//        return model;
//    }
//
//    //*************************media*******************************//
//    @RequestMapping("/media/getAll")
//    public ModelAndView getAllMedia() {
//        ModelAndView model = new ModelAndView();
//        List<Media> medias = mediaService.getAllMedia();
//
//        model.addObject("medias", medias);
//        model.setViewName("medias");
//        return model;
//    }
//
//    @GetMapping("/media/{id}")
//    public ModelAndView getMedia(HttpServletRequest request, @PathVariable String id) {
//        HttpSession session = request.getSession();
//        int viewCount = setView((String) session.getAttribute("userID"), id);
//
//        ModelAndView model = new ModelAndView();
//        Media media = mediaService.getMedia(id);
//        System.out.println(media.toString());
//
//        model.addObject("media", media);
//        model.addObject("viewCount", viewCount);
//        model.setViewName("media");
//
//        return model;
//    }
//
//    @GetMapping("/media/get/{id}")
//    public ResponseEntity getMediaRest(@PathVariable String id, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        int viewCount = setView((String) session.getAttribute("userID"), id);
//
//        Media media = mediaService.getMedia(id);
//        System.out.println("media: " + media.toString());
//        if (media == null)
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/json"))
//                .cacheControl(CacheControl.noCache().mustRevalidate())
//                .body(media);
//    }
//
//    public int setView(String userID, String mediaID) {
//        User user = userService.getUser(userID);
//        Media media = mediaService.getMedia(mediaID);
//
//        List view = user.getViewMedia();
////        view.add(media);
////        user.setViewMedia(media);
//        userService.updateUser(user);
//
//        return view.size();
//    }
//
//    @GetMapping("/media/download/{id}")
//    public ResponseEntity downloadMedia(@PathVariable String id) throws IOException {
//        Media media = mediaService.getMedia(id);
//        String mediaName = media.getName();
//
//        Path path = Paths.get(BASE_DIR + media.getPath());
//        String mimeType = Files.probeContentType(path);
//        Resource resource = null;
//        try {
//            resource = new UrlResource(path.toUri());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.valueOf(mimeType))
////                .contentType(MediaType.parseMediaType("application/json"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaName + "\"")
//                .contentLength(resource.contentLength())
//                .cacheControl(CacheControl.noCache().mustRevalidate())
//                .body(resource);
//    }
//
//    @PostMapping("/media/upload")
//    public ResponseEntity addMedia(@RequestParam("file") MultipartFile file, @RequestParam("albumName") String albumName,
//                                   @RequestParam("mediaName") String mediaName, @RequestParam("genre") String genre
//            , @RequestParam("artistID") String artistID) {
//
//        if (file.isEmpty())
//            return (ResponseEntity) ResponseEntity.noContent();
//
//        try {
//            // Get the file and save it somewhere
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(BASE_DIR + file.getOriginalFilename());
//            Files.write(path, file.getBytes());
//
//            System.out.println("nnn" + albumName);
//
////            Album album = albumService.getAlbumByName(albumName);
//            Artist artist = artistService.getArtist(artistID);
////            Artist artist = artistService.getArtistByName(artistName.split(" ")[0], artistName.split(" ")[1]);
////            if (album == null && artist == null) {
////                System.out.println("The required media or artist is not exists !!");
////                return new ResponseEntity(HttpStatus.NOT_FOUND);
////            }
//
//            Media uploadMedia = new Media();
//            uploadMedia.setName(mediaName);
//            uploadMedia.setArtist(artist);
////            uploadMedia.setAlbum(album);
//            uploadMedia.setArtist(artist);
//            uploadMedia.setLength((int) file.getSize());
//            uploadMedia.setGenre(genre);
//            uploadMedia.setPath(file.getOriginalFilename());
//
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            Date date = new Date();
//            uploadMedia.setPublishDate(formatter.format(date));
//
//
////            if (artist != null)
////                album.setCompiles(artist);
//
//
//            mediaService.addOrUpdateMedia(uploadMedia);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/media/delete/{id}")
//    public ResponseEntity deleteMedia(@PathVariable("id") String id) {
//        boolean response = mediaService.deleteMedia(id);
//        if (response)
//            return new ResponseEntity<>(HttpStatus.OK);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @RequestMapping("/media/update/{id}")
//    public ResponseEntity updateMedia(@PathVariable("id") String id, @RequestParam("score") int score,
//                                      @RequestParam("name") String name, @RequestParam("genre") String genre,
//                                      @RequestParam("length") int length, @RequestParam("publishDate") String publishDate) {
//        Media media = mediaService.getMedia(id);
//        if (media != null) {
//            media.setName(name);
//            media.setGenre(genre);
//            media.setLength(length);
//            media.setPublishDate(publishDate);
//            media.setScore(score);
//            mediaService.addOrUpdateMedia(media);
//            return new ResponseEntity(HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//}
