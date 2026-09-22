package pekan2;

import java.util.ArrayList;

public class Rekening2 {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	// Implementasi Asosiasi (1-to-many)
	ArrayList<Transaksi> riwayatTransaksi;
	
	public Rekening2(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
		
		// Wajib menginisialisasi ArrayList di dalam constructor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<>();
		
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
	}
	
	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
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
		System.out.println("---------------------");
	}
	
	public void tarikTunai(double nominal) {
		if (nominal <= 10000) {
			System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000");
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp." + saldo);
		} else {
			saldo = saldo - nominal;
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Tarik tunai Rp." + nominal + " berhasil");
			System.out.println("Saldo anda sekarang: Rp." + saldo);
		}
	}
	
	public void cetakMutasi() {
		System.out.println("=== MUTASI REKENING " + nomorRekening + " ===");
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini.");
		} else {
			for (Transaksi trx : riwayatTransaksi) {
				trx.cetakDetail();
			}
		}
	}
	
	public void cetakRingkasan() {
		double totalSetor = 0;
		double totalTarik = 0;
		
		for (Transaksi trx : riwayatTransaksi) {
			if (trx.jenis.equals("Kredit")) {
				totalSetor += trx.nominal;
			} else if (trx.jenis.equals("Debit")) {
				totalTarik += trx.nominal;
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
