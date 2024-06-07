// Declaraciones de clases

public abstract class Empleado {
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected int cantidadHijos;

    public Empleado(String nombre, String apellido, String cedula, int cantidadHijos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.cantidadHijos = cantidadHijos;
    }

    public abstract double calcularSueldo();
}

class EmpleadoPorHora extends Empleado {
    private int horasTrabajadas;

    public EmpleadoPorHora(String nombre, String apellido, String cedula, int cantidadHijos, int horasTrabajadas) {
        super(nombre, apellido, cedula, cantidadHijos);
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public double calcularSueldo() {
        return horasTrabajadas * 100;
    }
}

class EmpleadoTemporal extends Empleado {
    private String fechaAlta;
    private String fechaBaja;

    public EmpleadoTemporal(String nombre, String apellido, String cedula, int cantidadHijos, String fechaAlta, String fechaBaja) {
        super(nombre, apellido, cedula, cantidadHijos);
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    @Override
    public double calcularSueldo() {
        return 18000 + (cantidadHijos * 1000);
    }
}

class EmpleadoPermanente extends Empleado {
    private int antiguedad;

    public EmpleadoPermanente(String nombre, String apellido, String cedula, int cantidadHijos, int antiguedad) {
        super(nombre, apellido, cedula, cantidadHijos);
        this.antiguedad = antiguedad;
    }

    @Override
    public double calcularSueldo() {
        return 20000 + (cantidadHijos * 1000) + (antiguedad * 1000);
    }
}
