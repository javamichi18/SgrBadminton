import {Injectable} from "@angular/core";
import {CanActivate} from "@angular/router";
import {Globals} from "../globals";

@Injectable()
export class AuthGuard implements CanActivate {
    private globals: Globals;

    canActivate() {
        self.console.log('globals = ' + this.globals + " / " + this.globals.getLoggedIn().id);

        let user = this.globals.getLoggedIn();
        if (user && user.admin) {
            let isAdmin = user.admin;
            self.console.log('AuthGuard#canActivate: ' + user.nachname + ' > ' + isAdmin);
            return isAdmin;
        }

        // TODO: false! use
        return true;
    }

    constructor(globals: Globals) {
        this.globals = globals;
    }
}