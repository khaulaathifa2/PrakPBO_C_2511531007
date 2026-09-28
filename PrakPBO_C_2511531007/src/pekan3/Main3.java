package pekan3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main3 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening3> daftarRekening = new ArrayList<>(); // Menyimpan semua rekening
		Rekening3 akunAktif = null; // Objek belum dinisialisasi (null)
		boolean isRunning = true;
		System.out.println("=== SISTEM PERBANKAN MINI ===");

		while (isRunning) {
			 System.out.println("\nMenu Utama:");
			 System.out.println("1. Buka Rekening Baru");
			 System.out.println("2. Setor Tunai");
			 System.out.println("3. Tarik Tunai");
			 System.out.println("4. Cek Informasi Rekening");
			 System.out.println("5. Ganti Akun");
			 System.out.println("6. Cetak Mutasi(Riwayat)");
			 System.out.println("7. Ringkasan Transaksi (challenge)");
			 System.out.println("0. Keluar");
			 System.out.print("Pilih menu: ");

			 int pilihan = input.nextInt();
			 input.nextLine(); // Membersihkan buffer enter

			 switch(pilihan) {
				 case 1:
					 System.out.print("Masukkan No Rekening: ");
					 String no = input.nextLine();
					 System.out.print("Masukkan Nama Pemilik: ");
					 String nama = input.nextLine();
					 System.out.print("Masukkan Saldo Awal: ");
					 double saldo = input.nextDouble();
					 input.nextLine();
					 System.out.print("Buat PIN (6 digit angka): ");
					 String  pinBaru = input.nextLine(); // buat PIN sebelum instansiasi
					
					 // Instansiasi object / Menjalankan constructor
					 Rekening3 rekeningBaru = new Rekening3(no, nama, saldo, pinBaru);
					 daftarRekening.add(rekeningBaru); // Tambahkan ke ArrayList, tidak menimpa yang lama
					 akunAktif= rekeningBaru; // Otomatis jadi akun aktif setelah dibuat
					 break;
				 case 2:
					 if (akunAktif == null) {
						 System.out.println("Error : Mohon maaf, Anda belum memiliki nomor rekening");
					 } else if (akunAktif.isTerblokir()) {
						 System.out.println("Akun anda terblokir");
					 } else {
						 System.out.print("Masukkan nominal setor: ");
						 double setor = input.nextDouble();
						 input.nextLine();
						
						 akunAktif.setorTunai(setor); // Memanggil Behavior / method 
					 }
					 break;
				 case 3:
					 if (akunAktif == null) {
						 System.out.println("Error : Mohon maaf, Anda belum memiliki nomor rekening");
					 } else if (akunAktif.isTerblokir()) {
						 System.out.println("Akun anda terblokir");
					 } else {
						 // Verifikasi PIN sebelum tarik tunai
						 System.out.print("Masukkan PIN: ");
						 String pinTarik = input.nextLine();
						
						 if (akunAktif.otentikasi(pinTarik)) {
							 System.out.print("Masukkan nominal tarik: ");
							 double tarik = input.nextDouble();
							 input.nextLine();
							 akunAktif.tarikTunai(tarik); 
						 } else if (akunAktif.isTerblokir()) {
							 System.out.println("Akun anda terblokir");
						 } else {
							 System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
						 }
					 }
					 break;
				 case 4:
					 if (akunAktif == null) {
						 System.out.println("Error: Anda belum membuka rekening!");
					 } else {
						 akunAktif.cekInformasi();
					 }
					 break;
				 case 5:
					 if (daftarRekening.isEmpty()) {
						 System.out.println("Error : Belum ada rekening yang terdaftar!");
					 } else {
						 System.out.print("Masukkan No Rekening yang ingin diaktifkan: ");
						 String cariNo = input.nextLine();
						
						 Rekening3 ditemukan = null;
						 for (Rekening3 r : daftarRekening) {
							 if (r.getNomorRekening().equals(cariNo)) {
								 ditemukan = r;
								 break;
							 }
						 }
						
						 if (ditemukan != null) {
							 akunAktif = ditemukan;
							 System.out.println("Berhasil beralih ke akun atas nama " + akunAktif.getNamaPemilik());
						 } else {
							 System.out.println("Error : Nomor rekening tidak ditemukan!");
						 }
					 }
					 break;
				 case 6:
					 if (akunAktif == null) {
						 System.out.println("Error: Anda belum membuka rekening!");
					 } else if (akunAktif.isTerblokir()) {
						 System.out.println("Akun anda terblokir");
					 } else {
						 // Verifikasi PIN sebelum cetak mutasi
						 System.out.print("Masukkan PIN: ");
						 String pinMutasi = input.nextLine();
						
						 if (akunAktif.otentikasi(pinMutasi)) {
							 akunAktif.cetakMutasi();
						 } else if (akunAktif.isTerblokir()) {
							 System.out.println("Akun anda terblokir");
						 } else {
							 System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
						 }
					 }
					 break;
				 case 7:
					 if (akunAktif == null) {
						 System.out.println("Error: Anda belum membuka rekening!");
					 } else {
						 akunAktif.cetakRingkasan();
					 }
					 break;
				 case 0:
					 isRunning = false;
					 System.out.println("Sistem ditutup. Terima kasih!");
					 break;
				default:
					System.out.println("Pilihan tidak valid!");
			 }
		 }
		 input.close();
	}

}