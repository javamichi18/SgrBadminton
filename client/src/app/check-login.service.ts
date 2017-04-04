/**
 * Created by Max on 29.03.2017.
 */
import {Injectable} from "@angular/core";
import {Router, RouterModule} from "@angular/router";

import {Spieler} from "./spieler";
import {SpielerService} from "./spieler.service";
import {LoginTry} from "./login.component";
import {isNullOrUndefined} from "util";
import {Globals} from "./globals";
import { AppRoutingModule } from './app-routing.module';

@Injectable()
export class CheckLoginService {
    constructor(private router: Router,
                private globals: Globals,
                private spielerservice: SpielerService) {
    }
    private values: Spieler[];
    private login: LoginTry;
    getRedirect(login: LoginTry): void {

        this.login = login;
        this.spielerservice.obsSpielerholen()
            .subscribe(
                function (response) {
                    this.values = response;
                },
                function (error) {
                    console.log("Error happened" + error);
                },
                function () {
                    console.log("the subscription is completed");
                    if (!isNullOrUndefined(this.values)) {
                        console.log();
                    }
                }
            );
    }
    doRedirect(): void {
        if (isNullOrUndefined(this.values.find(spieler => spieler.email === this.login.loginname && spieler.email === this.login.passwort)
            )) {
            this.router.navigate(['/']);
        } else {
            this.globals.setLoggedIn(this.values.find(spieler => spieler.email === this.login.loginname && spieler.email === this.login.passwort));
            this.router.navigate(['/ranking']);
        }
    }
}
