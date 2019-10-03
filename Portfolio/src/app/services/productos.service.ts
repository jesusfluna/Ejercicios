import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { producto } from '../interfaces/producto.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {
  public cargando: boolean = true;
  public productos: producto[] = [];
  public productosFiltrados: producto[] = [];

  constructor(private http: HttpClient) {
    this.cargarProductos();
   }

  private cargarProductos(){
    return new Promise ((resolve, reject) => {

      this.http.get("https://angular-html-ebed4.firebaseio.com/productos_idx.json").subscribe(
        (resp: producto[])=>{

          this.productos = resp;
          this.cargando = false;
          resolve();
      });

    });
  }

  public getProducto(id: String){
    return this.http.get(`https://angular-html-ebed4.firebaseio.com/productos/${id}.json`)
  }

  public buscarProducto(cadena: string){
    //Cargar productos
    if(this.productos.length == 0){
      //Ejecutar desues de cargar los productos
      this.cargarProductos().then(()=>{
      this.filtrarProductos(cadena);
      });
    }else{
      //Aplicar el filtro
      this.filtrarProductos(cadena);
    }
  }

  public filtrarProductos(termino: string){
   
    termino = termino.toLowerCase();
    this.productosFiltrados = [];
    this.productos.forEach( prod => {

      let tituloLowe = prod.titulo.toLowerCase();

      if(prod.categoria.indexOf(termino)>=0 || tituloLowe.indexOf(termino)>=0){
        this.productosFiltrados.push(prod);
      }

    });

  }
}

// setTimeout(()=>{//Timer para esperar 2s antes de cargar para mostrar el loader
//   this.cargando = false;
// },1000);