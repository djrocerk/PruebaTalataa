export interface Pedido {
    id: number;
    fecha:    Date;
    total:    number;
    detalles: Detalleorder[];
}

export interface Detalleorder {
    id: number;
    productos:       Productos;
    pedido?:         Pedido;
    cantidad:        number;
    precioUnitario:  number;
    subtotal:        number;
}

export interface Productos {
    id:  number;
    nombre:      string;
    descripcion: string;
    imagen:      string;
    stock:       number;
    precio:      number;
}
