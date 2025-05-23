import java.util.*;

class Movie {
    private Long id;
    private String title;

    public Movie(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return id + ": " + title;
    }
}

class Showtime {
    private Long id;
    private String time;
    private Theater theater;
    private Movie movie;

    public Showtime(Long id, String time, Theater theater, Movie movie) {
        this.id = id;
        this.time = time;
        this.theater = theater;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public Theater getTheater() {
        return theater;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return id + ": " + time + " tại " + theater.getName();
    }
}

class Seat {
    private Long id;
    private boolean isAvailable = true;

    public Seat(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookSeat() {
        this.isAvailable = false;
    }

    @Override
    public String toString() {
        return "Ghế " + id + (isAvailable ? " (Còn trống)" : " (Đã đặt)");
    }
}

class Theater {
    private Long id;
    private String name;
    private List<Seat> seats;

    public Theater(Long id, String name, int seatCount) {
        this.id = id;
        this.name = name;
        this.seats = new ArrayList<>();
        for (long i = 1; i <= seatCount; i++)
            seats.add(new Seat(i));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}

class User {
    private Long id;
    private String name;
    private String username;
    private String password;

    public User(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

class Manager {
    private Long id;
    private String name;
    private String username;
    private String password;

    public Manager(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

class Booking {
    private User user;
    private Movie movie;
    private Showtime showtime;
    private Seat seat;

    public Booking(User user, Movie movie, Showtime showtime, Seat seat) {
        this.user = user;
        this.movie = movie;
        this.showtime = showtime;
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Khách: " + user.getName() + " | Phim: " + movie.getTitle() +
                " | Suất: " + showtime.getTime() + " | Ghế: " + seat;
    }
}



class Payment {
    public static boolean processPayment(double amount) {
        System.out.println("Thanh toán " + amount + "k thành công!");
        return true;
    }
}

// Cập nhật lớp SystemManager
class SystemManager {
    private List<Movie> movies;
    private List<Theater> theaters;
    private List<Showtime> showtimes;
    private List<Booking> bookings;
    private List<User> users;
    private List<Manager> managers;
    private double totalRevenue = 0;

    public SystemManager() {
        movies = new ArrayList<>();
        theaters = new ArrayList<>();
        showtimes = new ArrayList<>();
        bookings = new ArrayList<>();
        users = new ArrayList<>();
        managers = new ArrayList<>();
        managers.add(new Manager(1L, "Admin", "admin", "12345"));
        users.add(new User(1L, "Khách hàng", "user", "67890"));
    }

    public void addRevenue(double amount) {
        this.totalRevenue += amount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public Map<Theater, Map<Showtime, Integer>> getSeatBookingStats() {
        Map<Theater, Map<Showtime, Integer>> stats = new HashMap<>();
        for (Booking booking : bookings) {
            Theater theater = booking.getShowtime().getTheater();
            Showtime showtime = booking.getShowtime();
            stats.putIfAbsent(theater, new HashMap<>());
            Map<Showtime, Integer> showtimeStats = stats.get(theater);
            showtimeStats.put(showtime, showtimeStats.getOrDefault(showtime, 0) + 1);
        }
        return stats;
    }

    // Phương thức xác thực
    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                return user;
            }
        }
        return null;
    }

    public Manager authenticateManager(String username, String password) {
        for (Manager manager : managers) {
            if (manager.getUsername().equals(username) && manager.authenticate(password)) {
                return manager;
            }
        }
        return null;
    }

    // Các phương thức quản lý Movie - chỉ dành cho Manager
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public boolean removeMovie(Long id) {
        return movies.removeIf(movie -> movie.getId().equals(id));
    }

    // Các phương thức quản lý Theater - chỉ dành cho Manager
    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    public boolean removeTheater(Long id) {
        return theaters.removeIf(theater -> theater.getId().equals(id));
    }

    // Các phương thức quản lý Showtime - chỉ dành cho Manager
    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    public boolean removeShowtime(Long id) {
        return showtimes.removeIf(showtime -> showtime.getId().equals(id));
    }

    // Các phương thức chung dành cho cả User và Manager
    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie getMovieById(Long id) {
        for (Movie movie : movies)
            if (movie.getId().equals(id))
                return movie;
        return null;
    }

    public List<Theater> getAllTheaters() {
        return theaters;
    }

    public List<Showtime> getAllShowtimes() {
        return showtimes;
    }

    public List<Showtime> getShowtimesForMovie(Long movieId) {
        List<Showtime> result = new ArrayList<>();
        for (Showtime showtime : showtimes) {
            if (showtime.getMovie().getId().equals(movieId))
                result.add(showtime);
        }
        return result;
    }

    public Showtime getShowtimeById(Long id) {
        for (Showtime showtime : showtimes)
            if (showtime.getId().equals(id))
                return showtime;
        return null;
    }

    // Quản lý đặt vé - cho phép User thực hiện
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    // Dữ liệu ban đầu
    public void initializeData() {
        Theater theater1 = new Theater(1L, "Rạp 1", 10);
        theaters.add(theater1);
        Movie movie1 = new Movie(1L, "Avengers: Endgame");
        Movie movie2 = new Movie(2L, "Inception");
        movies.add(movie1);
        movies.add(movie2);
        showtimes.add(new Showtime(1L, "10:00", theater1, movie1));
        showtimes.add(new Showtime(2L, "13:00", theater1, movie2));
    }
}

// Lớp MovieBookingSystem cập nhật menu và chức năng mới
public class MovieBookingSystem {
    private static SystemManager systemManager = new SystemManager();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static Manager currentManager = null;

    public static void main(String[] args) {
        systemManager.initializeData();
        try {
            System.out.println("===== HỆ THỐNG ĐẶT VÉ XEM PHIM =====");
            boolean running = true;
            while (running) {
                if (currentUser == null && currentManager == null) {
                    showLoginMenu();
                } else if (currentManager != null) {
                    showManagerMenu();
                } else if (currentUser != null) {
                    showUserMenu();
                }
            }
        } finally {
            scanner.close();
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== ĐĂNG NHẬP ===");
        System.out.println("1. Đăng nhập với vai trò Quản lý");
        System.out.println("2. Đăng nhập với vai trò Khách hàng");
        System.out.println("3. Thoát");
        System.out.print("Chọn: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    loginAsManager();
                    break;
                case 2:
                    loginAsUser();
                    break;
                case 3:
                    System.out.println("Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số!");
        }
    }

    private static void loginAsManager() {
        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();
        Manager manager = systemManager.authenticateManager(username, password);
        if (manager != null) {
            currentManager = manager;
            System.out.println("Đăng nhập thành công! Xin chào, " + manager.getName());
        } else {
            System.out.println("Đăng nhập thất bại! Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }

    private static void loginAsUser() {
        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();
        User user = systemManager.authenticateUser(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Đăng nhập thành công! Xin chào, " + user.getName());
        } else {
            System.out.println("Đăng nhập thất bại! Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }

    // Menu quản lý - có thể thêm/xóa các đối tượng
    // Cập nhật menu quản lý
    private static void showManagerMenu() {
        System.out.println("\n=== MENU QUẢN LÝ ===");
        System.out.println("1. Thêm phim mới");
        System.out.println("2. Xóa phim");
        System.out.println("3. Thêm rạp chiếu");
        System.out.println("4. Thêm suất chiếu");
        System.out.println("5. Xem danh sách phim");
        System.out.println("6. Xem doanh thu");
        System.out.println("7. Xem thống kê ghế đã đặt");
        System.out.println("8. Xem danh sách đặt vé");
        System.out.println("9. Đăng xuất");
        System.out.print("Chọn: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    removeMovie();
                    break;
                case 3:
                    addTheater();
                    break;
                case 4:
                    addShowtime();
                    break;
                case 5:
                    displayMovies();
                    break;
                case 6:
                    displayRevenue();
                    break;
                case 7:
                    displaySeatStats();
                    break;
                case 8:
                    displayBookings();
                    break;
                case 9:
                    currentManager = null;
                    System.out.println("Đã đăng xuất.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số!");
        }
    }

    // Thêm 2 phương thức mới
    private static void displayRevenue() {
        System.out.printf("\n=== TỔNG DOANH THU: %.1fk ===\n", systemManager.getTotalRevenue());
    }

    private static void displaySeatStats() {
        Map<Theater, Map<Showtime, Integer>> stats = systemManager.getSeatBookingStats();
        System.out.println("\n=== THỐNG KÊ GHẾ ĐÃ ĐẶT ===");
        for (Theater theater : stats.keySet()) {
            System.out.println("├─ Rạp: " + theater.getName());
            Map<Showtime, Integer> showtimeStats = stats.get(theater);
            for (Showtime showtime : showtimeStats.keySet()) {
                System.out.println("│   ├─ Suất: " + showtime.getTime() +
                        " | Phim: " + showtime.getMovie().getTitle() +
                        " | Số ghế đã đặt: " + showtimeStats.get(showtime));
            }
        }
        if (stats.isEmpty()) {
            System.out.println("Chưa có ghế nào được đặt.");
        }
    }

    // Menu người dùng - chỉ có thể xem và đặt vé
    private static void showUserMenu() {
        System.out.println("\n=== MENU KHÁCH HÀNG ===");
        System.out.println("1. Xem danh sách phim");
        System.out.println("2. Đặt vé");
        System.out.println("3. Đăng xuất");
        System.out.print("Chọn: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    displayMovies();
                    break;
                case 2:
                    selectMovieAndBooking();
                    break;
                case 3:
                    currentUser = null;
                    System.out.println("Đã đăng xuất.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số!");
        }
    }

    // Thêm phim mới (chỉ dành cho quản lý)
    private static void addMovie() {
        System.out.print("Tên phim: ");
        String movieName = scanner.nextLine().trim();
        while (movieName.isEmpty()) {
            System.out.print("Tên phim không được để trống. Nhập lại: ");
            movieName = scanner.nextLine().trim();
        }
        Movie movie = new Movie((long) (systemManager.getAllMovies().size() + 1), movieName);
        systemManager.addMovie(movie);
        System.out.println("Đã thêm phim: " + movie);
    }

    // Xóa phim (chỉ dành cho quản lý)
    private static void removeMovie() {
        displayMovies();
        System.out.print("Nhập ID phim cần xóa: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            if (systemManager.removeMovie(id)) {
                System.out.println("Đã xóa phim có ID: " + id);
            } else {
                System.out.println("Không tìm thấy phim có ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phải là số!");
        }
    }

    // Thêm rạp (chỉ dành cho quản lý)
    private static void addTheater() {
        System.out.print("Tên rạp: ");
        String theaterName = scanner.nextLine().trim();
        while (theaterName.isEmpty()) {
            System.out.print("Tên rạp không được để trống. Nhập lại: ");
            theaterName = scanner.nextLine().trim();
        }
        int seatCount = readPositiveInt("Số ghế trong rạp: ");
        Theater theater = new Theater((long) (systemManager.getAllTheaters().size() + 1), theaterName, seatCount);
        systemManager.addTheater(theater);
        System.out.println("Đã thêm rạp: " + theater.getName() + " với " + seatCount + " ghế");
    }

    // Thêm suất chiếu (chỉ dành cho quản lý)
    private static void addShowtime() {
        // Hiển thị danh sách phim
        displayMovies();
        if (systemManager.getAllMovies().isEmpty()) {
            System.out.println("Chưa có phim nào. Vui lòng thêm phim trước.");
            return;
        }

        // Chọn phim
        Long movieId = null;
        Movie selectedMovie = null;
        while (selectedMovie == null) {
            System.out.print("Nhập ID phim: ");
            try {
                movieId = Long.parseLong(scanner.nextLine());
                selectedMovie = systemManager.getMovieById(movieId);
                if (selectedMovie == null)
                    System.out.println("Không tìm thấy phim này.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

        // Hiển thị danh sách rạp
        List<Theater> theaters = systemManager.getAllTheaters();
        if (theaters.isEmpty()) {
            System.out.println("Chưa có rạp nào. Vui lòng thêm rạp trước.");
            return;
        }
        System.out.println("=== DANH SÁCH RẠP ===");
        for (Theater theater : theaters) {
            System.out.println(theater.getId() + ": " + theater.getName());
        }

        // Chọn rạp
        Long theaterId = null;
        Theater selectedTheater = null;
        while (selectedTheater == null) {
            System.out.print("Nhập ID rạp: ");
            try {
                theaterId = Long.parseLong(scanner.nextLine());
                for (Theater theater : theaters) {
                    if (theater.getId().equals(theaterId)) {
                        selectedTheater = theater;
                        break;
                    }
                }
                if (selectedTheater == null)
                    System.out.println("Không tìm thấy rạp này.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

        // Nhập thời gian
        String time;
        do {
            System.out.print("Thời gian suất chiếu (ví dụ: 19:00): ");
            time = scanner.nextLine().trim();
        } while (time.isEmpty());

        // Tạo và thêm suất chiếu
        Showtime showtime = new Showtime((long) (systemManager.getAllShowtimes().size() + 1),
                time, selectedTheater, selectedMovie);
        systemManager.addShowtime(showtime);
        System.out.println("Đã thêm suất chiếu: " + showtime);
    }

    private static int readPositiveInt(String prompt) {
        int value = -1;
        while (value <= 0) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value <= 0)
                    System.out.println("Giá trị phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
        return value;
    }

    // Hiển thị danh sách phim (chung cho tất cả)
    private static void displayMovies() {
        System.out.println("\n=== DANH SÁCH PHIM HIỆN CÓ ===");
        List<Movie> movies = systemManager.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("Chưa có phim nào.");
            return;
        }
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    // Hiển thị danh sách đặt vé (chỉ dành cho quản lý)
    private static void displayBookings() {
        System.out.println("\n=== DANH SÁCH ĐẶT VÉ ===");
        List<Booking> bookings = systemManager.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Chưa có đặt vé nào.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    // Chức năng đặt vé (dành cho người dùng)
    private static void selectMovieAndBooking() {
        displayMovies();
        List<Movie> movies = systemManager.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("Chưa có phim nào.");
            return;
        }

        Long movieId = null;
        Movie selectedMovie = null;
        while (selectedMovie == null) {
            System.out.print("\nNhập ID phim muốn đặt vé: ");
            try {
                movieId = Long.parseLong(scanner.nextLine());
                selectedMovie = systemManager.getMovieById(movieId);
                if (selectedMovie == null)
                    System.out.println("Không tìm thấy phim này.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

        List<Showtime> availableShowtimes = systemManager.getShowtimesForMovie(movieId);
        if (availableShowtimes.isEmpty()) {
            System.out.println("Không có suất chiếu cho phim này.");
            return;
        }
        System.out.println("\n=== CÁC SUẤT CHIẾU ===");
        for (Showtime showtime : availableShowtimes) {
            System.out.println(showtime);
        }

        Showtime selectedShowtime = null;
        while (selectedShowtime == null) {
            System.out.print("\nNhập ID suất chiếu: ");
            try {
                Long showtimeId = Long.parseLong(scanner.nextLine());
                for (Showtime showtime : availableShowtimes) {
                    if (showtime.getId().equals(showtimeId)) {
                        selectedShowtime = showtime;
                        break;
                    }
                }
                if (selectedShowtime == null)
                    System.out.println("Không tìm thấy suất chiếu này.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

        Theater theater = selectedShowtime.getTheater();
        System.out.println("\n=== DANH SÁCH GHẾ ===");
        for (Seat seat : theater.getSeats()) {
            System.out.println(seat);
        }

        Seat selectedSeat = null;
        while (selectedSeat == null) {
            System.out.print("\nNhập ID ghế muốn đặt: ");
            try {
                Long seatId = Long.parseLong(scanner.nextLine());
                for (Seat seat : theater.getSeats()) {
                    if (seat.getId().equals(seatId)) {
                        if (!seat.isAvailable()) {
                            System.out.println("Ghế đã được đặt. Chọn ghế khác.");
                        } else {
                            selectedSeat = seat;
                        }
                        break;
                    }
                }
                if (selectedSeat == null)
                    System.out.println("Không tìm thấy ghế này hoặc ghế đã được đặt.");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

        selectedSeat.bookSeat();
        Booking booking = new Booking(currentUser, selectedMovie, selectedShowtime, selectedSeat);
        systemManager.addBooking(booking);
        Payment.processPayment(100.0); // Giá vé mẫu
        systemManager.addRevenue(100.0); // <--- THÊM DÒNG NÀY
        System.out.println("Đặt vé thành công!");
        System.out.println(booking);

    }
}
