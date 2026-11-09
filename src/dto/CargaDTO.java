package dto;

public class CargaDTO extends ElementoDTO {
	private final boolean explotada;

	public CargaDTO(double x, double y, double ancho, double alto, boolean explotada) {
		super(x, y, ancho, alto);
		this.explotada = explotada;
	}

	public boolean isExplotada() {
		return explotada;
	}
}