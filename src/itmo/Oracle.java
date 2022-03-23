package itmo;

public interface Oracle {
    /**
     * Мудрый оракул, который знает значение функции в указанной точке
     * @param x - точка, значение в которой требуется узнать
     * @return значение в этой точке
     */
    double askValue(double x);
}
