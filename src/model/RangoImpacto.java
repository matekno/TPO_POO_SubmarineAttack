package model;

public enum RangoImpacto {
	LEJANO(30, 0, false), // mas de 100 m: suma puntos y no hace dano
	MEDIO(10, 30, false), // entre 50 y 100 m
	CERCANO(0, 50, false), // entre 10 y 50 m
	DIRECTO(0, 0, true); // menos de 10 m: pierde una vida

	private final int puntos;
	private final int porcentajeDano;
	private final boolean pierdeVida;

	RangoImpacto(int puntos, int porcentajeDanio, boolean pierdeVida) {
		this.puntos = puntos;
		this.porcentajeDano = porcentajeDanio;
		this.pierdeVida = pierdeVida;
	}

	public static RangoImpacto segunDistancia(double distancia) {
		// TODO: Sacar lo hardcodeado acá.
		if (distancia < 10) {
			return DIRECTO;
		}
		if (distancia < 50) {
			return CERCANO;
		}
		if (distancia <= 100) {
			return MEDIO;
		}
		return LEJANO;
	}

	public boolean haceDano() {
		if (pierdeVida == true) {
			return true;
		} else if (porcentajeDano > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getPuntos() {
		return puntos;
	}

	public int getPorcentajeDano() {
		return porcentajeDano;
	}

	public boolean pierdeVida() {
		return pierdeVida;
	}
}