package pekan5;

public class RekeningVIP2 extends Rekening5 {
	private double bonus = 100000;
	
	public RekeningVIP2(String nomor, String nama, double saldoAwal, String pinAwal) {
		// Memanggil inisialisasi dasar dari Superclass
		super(nomor, nama, saldoAwal, pinAwal);
		
		saldo += bonus;
		
		// Mencatat riwayat transaksi
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi4(idTrx, "Bonus", bonus));
				
		System.out.println("Bonus pendaftaran sebesar Rp " + bonus + " berhasil masuk ke rekening anda.");
		System.out.println("Total saldo anda saat ini Rp " + saldo);
	}
}
