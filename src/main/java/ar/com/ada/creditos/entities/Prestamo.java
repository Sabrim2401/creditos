package ar.com.ada.creditos.entities;

import java.math.*;
import java.util.*;
import javax.persistence.*;

@Entity // uso el de Javax
@Table(name = "prestamo")

public class Prestamo {
    @Id
    @Column(name = "prestamo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENTAL
    private int prestamoId;

    @Temporal(TemporalType.DATE) // SOLO Poner esto si no queremos manejar HORA en el DB Server.
    private Date fecha;

    private BigDecimal importe;

    private int cuotas;

    @Column(name = "fecha_alta")
    private Date fechaAlta; // Es fecha/hora, no se necesita modificar

    @Column(name = "estado_id")
    private int estadoId; // Por ahora sera un int.

    @ManyToOne // join columns van donde esta la ForeingKey
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    // el primero es columna de prestamo, el segundo referencia columna de cliente
    private Cliente cliente;

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.cliente.agregarPrestamo(this); // relacion bidireccional.
        // Un cliente tiene muchos prestamos y a su vez un prestamo tiene un cliente.
    }

    // ENUMERADO

    public enum EstadoPrestamoEnum {
        SOLICITADO(1),
        RECHAZADO(2), 
        PENDIENTE_APROBACION(3), 
        APROBADO(4), 
        INCOBRABLE(5), 
        CANCELADO(6),
        PREAPROBADO(100);

        private final int value;

        // NOTE: Enum constructor tiene que estar en privado
        private EstadoPrestamoEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EstadoPrestamoEnum parse(int id) {
            EstadoPrestamoEnum status = null; // Default
            for (EstadoPrestamoEnum item : EstadoPrestamoEnum.values()) {
                if (item.getValue() == id) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}
