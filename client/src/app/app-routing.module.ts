/**
 * Created by Max on 28.03.2017.
 */
import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {AuthGuard} from "./admin/auth-guard.service";

import {RankingComponent} from "./ranking.component";
import {AdminComponent} from "./admin/admin.component";
import {SpielerDetailComponent} from "./admin/spieler-detail/spieler-detail.component";
import {ForderungenComponent} from "./forderungen.component";

const routes: Routes = [
    {path: '', redirectTo: '/ranking', pathMatch: 'full'},
    {path: 'ranking', component: RankingComponent},
    {path: 'forderungen', component: ForderungenComponent },
    {path: 'admin', component: AdminComponent, canActivate : [AuthGuard]},
    {path: 'admin/detail/:id', component: SpielerDetailComponent, canActivate : [AuthGuard]}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
