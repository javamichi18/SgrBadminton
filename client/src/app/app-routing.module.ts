/**
 * Created by Max on 28.03.2017.
 */
import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthGuard} from "./admin/auth-guard.service";

import {LoginComponent} from "./login.component";
import {RankingComponent} from "./ranking.component";
import {AdminComponent} from "./admin/admin.component";
import {SpielerDetailComponent} from "./admin/spieler-detail/spieler-detail.component";

const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
    {path: 'ranking', component: RankingComponent},
    {path: 'admin', component: AdminComponent, canActivate : [AuthGuard]},
    {path: 'admin/detail/:id', component: SpielerDetailComponent, canActivate : [AuthGuard]}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
