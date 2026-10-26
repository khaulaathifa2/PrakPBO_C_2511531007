package pekan5;


public class RekeningTabungan2 extends Rekening5 {
	
	// Atribut spesifik yang hanya dimiliki oleh Tabungan
	private double sukuBunga;
	
	// Constructor Subclass
	public RekeningTabungan2(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		//super() memanggil constructor kelas induk (Rekening). WAJIB berada di baris pertama!
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
	}
	
	public void tambahBungaAkhirBulan() {
		// Menghitung bunga
		// Mengapa bisa mengakses saldo secara langsung dari class RekeningTabungan ?
		double nominalBunga = saldo * (sukuBunga / 100);
		saldo += nominalBunga;
		
		// Mencatat riwayat transaksi
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi4(idTrx, "Bunga", nominalBunga));
		
		System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan: Rp" + nominalBunga);
	}
}
