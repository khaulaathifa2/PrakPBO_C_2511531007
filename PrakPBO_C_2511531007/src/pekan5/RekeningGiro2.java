package pekan5;

public class RekeningGiro2 extends Rekening5 {
	private double batasOverdraft;
	
	public RekeningGiro2(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		// Memanggil inisialisasi dasar dari Superclass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	// IMPLEMENTASI POLIMORFISME (OVERRIDING)
	@Override
	public boolean tarikTunai(double nominal) {
		if (nominal <= 0) {
			System.out.println("Transaksi Gagal: Nominal tarik harus lebih dari 0!");
			return false;
		}
		// Logika Khusus Giro: Boleh ditarik asalkan nominal tidak melebihi (saldo + batasOverdraft)
		else if (nominal > (saldo + batasOverdraft)) {
			System.out.println("Transaksi Gagal: Melebihi batas overdraft! Maksimalkan penarikan: Rp" + (saldo + batasOverdraft));
			return false;
		}
		else {
			saldo -= nominal; // Saldo bisa bernilai negatif di sini (memakan overdraft)
			
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			Transaksi4 trxBaru = new Transaksi4(idTrx, "Debit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Tarik tunai Giro Rp" + nominal + " berhasil.");
			System.out.println("Saldo anda sekarang: Rp." + saldo);
			return true; // Transaksi sukses
		}
	}
	
	// Getter khusu Giro
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
}
