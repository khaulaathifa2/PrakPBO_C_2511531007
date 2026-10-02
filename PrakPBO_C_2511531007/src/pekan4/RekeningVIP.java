package pekan4;

public class RekeningVIP extends Rekening4 {
	private double bonus = 100000;
	
	public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
		// Memanggil inisialisasi dasar dari Superclass
		super(nomor, nama, saldoAwal, pinAwal);
		
		saldo += bonus;
		
		// Mencatat riwayat transaksi
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi3(idTrx, "Bonus", bonus));
				
		System.out.println("Bonus pendaftaran sebesar Rp " + bonus + " berhasil masuk ke rekening anda.");
		System.out.println("Total saldo anda saat ini Rp " + saldo);
	}
}
