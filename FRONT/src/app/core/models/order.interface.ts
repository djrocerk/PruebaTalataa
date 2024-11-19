export interface Detalle {
    idDetallepedido: number;
    productos:       Productos;
    pedido?:         Order;
    cantidad:        number;
    precioUnitario:  number;
    subtotal:        number;
}

export interface Order {
    idPedido: number;
    fecha:    Date;
    total:    number;
    detalles: Detalle[];
}

export interface Productos {
    idProducto:  number;
    imagen:      string;
    stock:       number;
    precio:      number;
}


