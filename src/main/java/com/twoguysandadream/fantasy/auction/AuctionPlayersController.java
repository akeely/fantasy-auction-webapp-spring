package com.twoguysandadream.fantasy.auction;


import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.twoguysandadream.fantasy.auction.model.AuctionPlayer;
import com.twoguysandadream.fantasy.auction.services.AuctionPlayersService;
import com.twoguysandadream.fantasy.auction.services.AuctionPlayersServiceImpl;
import com.twoguysandadream.fantasy.auction.services.exception.AuctionPlayersServiceException;

@Controller
public class AuctionPlayersController {

	private final AuctionPlayersService auctionPlayersService;
	
	@Inject
	public AuctionPlayersController(AuctionPlayersServiceImpl auctionPlayersService) {
		
		this.auctionPlayersService=auctionPlayersService;
	}
	
	@RequestMapping(value="leagues/{leagueId}/auctionPlayers", method=RequestMethod.GET)
	public String getAuctionPlayers(@PathVariable int leagueId, Model model) throws AuctionPlayersServiceException {
		
			model.addAttribute("auctionPlayers", auctionPlayersService.getAuctionPlayers(leagueId));

		return "auctionPlayers/view";
	}
	
	@RequestMapping(value="leagues/{leagueId}/auctionPlayers/{playerId}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putAuctionPlayer(@PathVariable int leagueId, @PathVariable int playerId, @RequestBody AuctionPlayer player) throws AuctionPlayersServiceException {
		
		// TODO: Validate player?
		
		auctionPlayersService.updateBid(leagueId, playerId, player.getTeamId(), player.getBid());
	}
	
	@RequestMapping(value="leagues/{leagueId}/auctionPlayers", method=RequestMethod.POST)
	public void postAuctionPlayer(@PathVariable int leagueId, @RequestBody AuctionPlayer player) throws AuctionPlayersServiceException {
		
		auctionPlayersService.addAuctionPlayer(leagueId, player.getPlayerId(), player.getTeamId(), player.getBid());
	}
}
