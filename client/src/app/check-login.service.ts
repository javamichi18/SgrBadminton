/**
 * Created by Max on 29.03.2017.
 */
import {Injectable, OnInit} from "@angular/core";
import {Router, RouterModule} from "@angular/router";

import {Spieler} from "./spieler";
import {SpielerService} from "./spieler.service";
import {LoginTry} from "./login.component";
import {isNullOrUndefined} from "util";
import {Globals} from "./globals";
import { AppRoutingModule } from './app-routing.module';
import {Observable} from "rxjs";

@Injectable()
export class CheckLoginService {
    constructor(private router: Router,
                private globals: Globals,
                private spielerservice: SpielerService) {
    }
    private values: Observable<Spieler[]> = this.spielerservice.obsSpielerholen();
    private login: LoginTry;
    doRedirect(login: LoginTry): void {
        this.login = login;
        this.values = this.spielerservice.obsSpielerholen();
        if (isNullOrUndefined(this.values.find(spieler => spieler[0].email === this.login.loginname && spieler[0].email === this.login.passwort)
            )) {
            this.router.navigate(['/']);
        } else {
            this.globals.setLoggedIn(this.values.find(spieler => spieler[0].email === this.login.loginname && spieler[0].email === this.login.passwort)[0]);
            this.router.navigate(['/ranking']);
        }
    }
}
