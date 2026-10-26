package pekan5;

import java.util.ArrayList;
import java.util.Scanner;

public class Main5 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening5> daftarRekening = new ArrayList<>(); // Menyimpan semua rekening
		Rekening5 akunAktif = null; // Objek belum dinisialisasi (null)
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
			System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
			System.out.println("8. Transfer");
			System.out.println("9. Ringkasan Transaksi (challenge)");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");

			int pilihan = input.nextInt();
			input.nextLine(); // Membersihkan buffer enter

			switch (pilihan) {
			case 1:
				System.out.print("Masukkan No Rekening: ");
				String no = input.nextLine();
				System.out.print("Masukkan Nama Pemilik: ");
				String nama = input.nextLine();
				System.out.print("Masukkan Saldo Awal: ");
				double saldo = input.nextDouble();
				input.nextLine();
				System.out.print("Buat PIN (6 digit angka): ");
				String pinBaru = input.nextLine(); // buat PIN sebelum instansiasi
				System.out.println("\nProduk: ");
				System.out.println("1. Tabungan Umum");
				System.out.println("2. Giro Bisnis");
				System.out.println("3. Rekening VIP (challenge)");
				System.out.print("Pilih produk: ");
				int produk = input.nextInt();
				input.nextLine();

				Rekening5 rekeningBaru = null; // tipe Superclass

				if (produk == 1) {
					System.out.print("Masukkan suku bunga (%): ");
					double sukuBunga = input.nextDouble();
					input.nextLine();
					// Upcasting: RekeningTabungan disimpan di variabel Rekening5
					rekeningBaru = new RekeningTabungan2(no, nama, saldo, pinBaru, sukuBunga);
				} else if (produk == 2) {
					System.out.print("Masukkan batas overdraft: ");
					double batasOverdraft = input.nextDouble();
					input.nextLine();
					// Upcasting: RekeningGiro disimpan di variabel Rekening5
					rekeningBaru = new RekeningGiro2(no, nama, saldo, pinBaru, batasOverdraft);
				} else if (produk == 3) {
					// Upcasting: rekeningVIP disimpan di variabel Rekening5
					rekeningBaru = new RekeningVIP2(no, nama, saldo, pinBaru);

				} else {
					System.out.println("Pilihan produk tidak valid! Pembukaan rekening dibatalkan.");
					break;
				}
				daftarRekening.add(rekeningBaru); // Tambahkan ke ArrayList, tidak menimpa yang lama
				akunAktif = rekeningBaru; // Otomatis jadi akun aktif setelah dibuat
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

					Rekening5 ditemukan = null;
					for (Rekening5 r : daftarRekening) {
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
				} else if (akunAktif instanceof RekeningTabungan2) {
					RekeningTabungan2 tab = (RekeningTabungan2) akunAktif; // downcasting
					tab.tambahBungaAkhirBulan();
				} else {
					System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
				}
				break;
			case 8:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else if (akunAktif.isTerblokir()) {
					System.out.println("Akun anda terblokir");
				} else {
					System.out.print("Masukkan No Rekening Tujuan: ");
					String noTujuan = input.nextLine();

					Rekening5 akunTujuan = null;

					for (Rekening5 r : daftarRekening) {
						if (r.getNomorRekening().equals(noTujuan)) {
							akunTujuan = r;
							break;
						}
					}
					if (akunTujuan.isTerblokir()) {
						System.out.println("Error: Rekening tujuan terblokir!");
					} else {
						System.out.print("Masukkan nominal transfer: ");
						double nominal = input.nextDouble();
						input.nextLine();

						System.out.print("Masukkan PIN: ");
						String pin = input.nextLine();

						akunAktif.transfer(akunTujuan, nominal, pin);
					}
				}
				break;
			case 9:
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