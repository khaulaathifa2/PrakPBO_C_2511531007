package pekan5;

import java.util.ArrayList;

public class Rekening5 {

	// 1. Mengunci atribut dengan 'private'
	private String nomorRekening;
	private String namaPemilik;
	private String pin; // Data sensitif!

	// percobaan PIN salah & status blokir 
	private int percobaanGagal = 0;
	private boolean terblokir = false;
	private static final int BATAS_PERCOBAAN = 3;

	// Implementasi Asosiasi (1-to-many)
	// Gunakan protected agar Subclass bisa mengaksesnya langsung
	protected double saldo;
	protected ArrayList<Transaksi4> riwayatTransaksi;

	// 2. Modifikasi Constructor untuk menerima PIN awal
	public Rekening5(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;

		// Validasi PIN di dalam Constructor
		if (pinAwal.length() == 6) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin = "123456";
		}

		this.riwayatTransaksi = new ArrayList<>();
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo " + saldo);
	}

	// 3. Getter untuk atribut yang diizinkan dibaca publik
	public String getNomorRekening() { return nomorRekening; }
	public String getNamaPemilik() { return namaPemilik; }

	// getter status blokir, hanya bisa dibaca dari luar, tidak bisa diubah langsung ---
	public boolean isTerblokir() { return terblokir; }

	// 4. Method Otentikasi Internal (Validasi Enkapsulasi)
	public boolean otentikasi(String inputPin) {
		if (terblokir) {
			return false; // Sudah terblokir, otentikasi selalu gagal
		}

		if (this.pin.equals(inputPin)) {
			percobaanGagal = 0; // reset hitungan kalau PIN benar
			return true;
		} else {
			percobaanGagal++;
			if (percobaanGagal >= BATAS_PERCOBAAN) {
				terblokir = true;
			}
			return false;
		}
	}

	public void setorTunai(double nominal) {
		if (terblokir) {
			// akun terblokir 
			System.out.println("Akun anda terblokir");
			return;
		}

		if (nominal > 0) {
			saldo += nominal;
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi4 trxBaru = new Transaksi4(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);

			System.out.println("Setor tunai Rp." + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}

	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir  : Rp." + saldo);
		System.out.println("Status       : " + (terblokir ? "TERBLOKIR" : "Aktif"));
		System.out.println("---------------------");
	}
	
	// Mengubah void menjadi boolean
	public boolean tarikTunai(double nominal) {
		if (terblokir) {
			// akun terblokir
			System.out.println("Akun anda terblokir");
			return false;
		}

		if (nominal <= 0) {
			System.out.println("Transaksi Gagal: Nominal tarik harus lebih dari 0!");
			return false;
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp." + saldo);
			return false;
		} else {
			saldo -= nominal;
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			Transaksi4 trxBaru = new Transaksi4(idTrx, "Debit", nominal);
			riwayatTransaksi.add(trxBaru);

			System.out.println("Tarik tunai Rp." + nominal + " berhasil");
			System.out.println("Saldo anda sekarang: Rp." + saldo);
			return true; // Transaksi sukses
		}
	}
	public void transfer(Rekening5 tujuan, double nominal, String pinInput) {
	    if (terblokir) {
	        System.out.println("Akun anda terblokir");
	        return;
	    }
	    if (!otentikasi(pinInput)) {
	        if (isTerblokir()) {
	            System.out.println("Akun anda terblokir");
	        } else {
	            System.out.println("Akses ditolak: PIN salah!");
	        }
	        return;
	    }
	    if (nominal <= 0) {
	        System.out.println("Transfer gagal: Nominal harus lebih dari 0!");
	        return;
	    }
	    if (tujuan.isTerblokir()) {
	        System.out.println("Transfer gagal: Rekening tujuan terblokir!");
	        return;
	    }
	    
	    boolean berhasil = this.tarikTunai(nominal);

	    if (berhasil) {
	        tujuan.setorTunai(nominal);
	        System.out.println("\nTransfer berhasil!");
	        System.out.println("Rekening tujuan: " + tujuan.getNomorRekening());
	        System.out.println("Nominal transfer: Rp." + nominal);
			System.out.println("Saldo anda sekarang: Rp." + saldo);
	        
	    } else {
	        System.out.println("Transfer gagal!");
	    }
	}
	
	public void cetakMutasi() {
		if (terblokir) {
			// akun terblokir 
			System.out.println("Akun anda terblokir");
			return;
		}

		System.out.println("=== MUTASI REKENING " + nomorRekening + " ===");
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini.");
		} else {
			for (Transaksi4 trx : riwayatTransaksi) {
				trx.cetakDetail();
			}
		}
	}

	public void cetakRingkasan() {
		double totalSetor = 0;
		double totalTarik = 0;

		for (Transaksi4 trx : riwayatTransaksi) {
			if (trx.getJenis().equals("Kredit")) {
				totalSetor += trx.getNominal();
			} else if (trx.getJenis().equals("Debit")) {
				totalTarik += trx.getNominal();
			}
		}

		double akumulasi = totalSetor - totalTarik;

		System.out.println("=== Ringkasan Rekening " + nomorRekening + " ===");
	    System.out.println("Total setor = Rp." + totalSetor);
	    System.out.println("Total tarik = Rp." + totalTarik);
	    System.out.println("Akumulasi   = Rp." + akumulasi);
	    System.out.println("Saldo       = Rp." + saldo);
	}
}