import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {SpielerService} from "./spieler.service";
import {Spieler} from "../spieler";

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css'],
    providers: [SpielerService]
})
export class AdminComponent implements OnInit {
    private _service: SpielerService;
    private router: Router;

    private title: String;

    constructor(service: SpielerService, router: Router) {
        this._service = service;
        this.router = router;
        this.title = "hurrga!";
        //this.title = this._service.getSpieler()[0].toString();
    }

    ngOnInit() {
    }

    public getSpieler(): Spieler[] {
        
        return this._service.getSpielerPlain();
        // let sp: Spieler[];
        // this._service.getSpieler()
        //     .then(spieler => sp = spieler);
        //
        // //self.console.log("getSpieler: " + sp);
        // return sp;
    }

    // gotoDetail(id: number): void {
    //     this.router.navigate(['/detail', id]);
    // }

}
