import { Routes } from '@angular/router';
import { InicioComponent } from './Componentes/inicio/inicio.component';
import { InicioSesionComponent } from './Componentes/inicio/inicio-sesion/inicio-sesion.component';
import { RegistroComponent } from './Componentes/inicio/registro/registro.component';
import { ErrorComponent } from './Componentes/Errores/error/error.component';
import { SeleccionEtiquetasComponent } from './Componentes/Seleccion-etiquetas/selecion-etiquetas/seleccion-etiquetas/seleccion-etiquetas.component';
import { EtiquetaComponent } from './Componentes/Seleccion-etiquetas/etiqueta/etiqueta/etiqueta.component';
import { MenuLectorComponent } from './Componentes/Menus/menu-lector/menu-lector/menu-lector.component';
import { MenuEditorComponent } from './Componentes/Menus/menu-editor/menu-editor/menu-editor.component';
import { MenuAdminComponent } from './Componentes/Menus/menu-admin/menu-admin/menu-admin.component';
import { MenuAnuncianteComponent } from './Componentes/Menus/menu-anunciante/menu-anunciante/menu-anunciante.component';
import { PublicarRevistaComponent } from './Componentes/Revistas/publicar-revista/publicar-revista/publicar-revista.component';
import { PrevisualizarComponent } from './Componentes/Revistas/previsualizar/previsualizar/previsualizar.component';
import { VisualizarComponent } from './Componentes/Revistas/visualizar/visualizar/visualizar.component';
import { EstadoRevistaComponent } from './Componentes/compAdmin/estado-revista/estado-revista/estado-revista.component';
import { VerPerfilComponent } from './Componentes/CompLector/ver-perfil/ver-perfil/ver-perfil.component';

export const routes: Routes = [
    {path: 'Inicio', component: InicioComponent},
    {path: 'InicioSesion', component: InicioSesionComponent},
    {path: 'Registro', component: RegistroComponent},
    {path: 'Error',component:ErrorComponent},
    {path: 'Etiquetas', component:SeleccionEtiquetasComponent},
    {path: 'Etiqueta',component: EtiquetaComponent},
    {path: 'InicioLector' , component: MenuLectorComponent},
    {path: 'InicioEditor', component:MenuEditorComponent},
    {path: 'InicioAdmin', component: MenuAdminComponent},
    {path: 'InicioAnunciante', component:MenuAnuncianteComponent},
    {path: 'crear-revista', component:PublicarRevistaComponent},
    {path: 'InicioLector/Previsualizar/:idRevista', component:PrevisualizarComponent},
    {path: 'InicioLector/Perfil/:usuario', component:VerPerfilComponent},
    {path: 'InicioLector/Visualizar/:idRevista', component:VisualizarComponent},
    {path: 'InicioAdmin/Visualizar/:idRevista', component:VisualizarComponent},
    {path: 'InicioAdmin/estadoRevista/:idRevista', component:EstadoRevistaComponent},
    {path: '', redirectTo: '/Inicio',pathMatch: 'full'}
];
