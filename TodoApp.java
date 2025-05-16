import java.io.*;
import java.util.*;

public class TodoApp {
    static final String FILE_NAME = "todolist.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENU TO-DO LIST ===");
            System.out.println("1. Lihat Daftar");
            System.out.println("2. Tambah Tugas");
            System.out.println("3. Ubah Tugas");
            System.out.println("4. Hapus Tugas");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");
            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanDaftar();
                    break;
                case "2":
                    tambahTugas(input);
                    break;
                case "3":
                    ubahTugas(input);
                    break;
                case "4":
                    hapusTugas(input);
                    break;
                case "5":
                    System.out.println("Terima kasih!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void tampilkanDaftar() {
        List<String> daftar = bacaTugas();
        if (daftar.isEmpty()) {
            System.out.println("Daftar kosong.");
        } else {
            System.out.println("Daftar Tugas:");
            for (int i = 0; i < daftar.size(); i++) {
                System.out.println((i + 1) + ". " + daftar.get(i));
            }
        }
    }

    static void tambahTugas(Scanner input) {
        System.out.print("Masukkan tugas baru: ");
        String tugas = input.nextLine();
        try (BufferedWriter tulis = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            tulis.write(tugas);
            tulis.newLine();
            System.out.println("Tugas berhasil ditambahkan.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan tugas.");
        }
    }

    static void ubahTugas(Scanner input) {
        List<String> daftar = bacaTugas();
        tampilkanDaftar();
        if (daftar.isEmpty()) return;

        System.out.print("Masukkan nomor tugas yang ingin diubah: ");
        int nomor = Integer.parseInt(input.nextLine()) - 1;
        if (nomor < 0 || nomor >= daftar.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }

        System.out.print("Masukkan isi tugas baru: ");
        String baru = input.nextLine();
        daftar.set(nomor, baru);
        simpanTugas(daftar);
        System.out.println("Tugas berhasil diubah.");
    }

    static void hapusTugas(Scanner input) {
        List<String> daftar = bacaTugas();
        tampilkanDaftar();
        if (daftar.isEmpty()) return;

        System.out.print("Masukkan nomor tugas yang ingin dihapus: ");
        int nomor = Integer.parseInt(input.nextLine()) - 1;
        if (nomor < 0 || nomor >= daftar.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }

        daftar.remove(nomor);
        simpanTugas(daftar);
        System.out.println("Tugas berhasil dihapus.");
    }

    static List<String> bacaTugas() {
        List<String> hasil = new ArrayList<>();
        try (BufferedReader baca = new BufferedReader(new FileReader(FILE_NAME))) {
            String baris;
            while ((baris = baca.readLine()) != null) {
                hasil.add(baris);
            }
        } catch (IOException e) {
            // File belum ada, tidak masalah
        }
        return hasil;
    }

    static void simpanTugas(List<String> daftar) {
        try (BufferedWriter tulis = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String tugas : daftar) {
                tulis.write(tugas);
                tulis.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan.");
        }
    }
}
