Every now and then, friends send me tweets dunking on restaking. None of the dunks really hit the mark. So I decided to write a dunk/reflection myself.
This article will be long, dense, and just focused on restaking. This article might be boring. And you might think I am being too soft on ourselves.
You may think I’m too close to the story to be objective or too proud to say “we miscalculated.” You may think this is going to be long explanation from someone who refuses to say the word “failed,” even when everyone else already has.
All of those are fair. And many of them have some truth in them.
This is simply the unvarnished version of what actually happened, what landed, what didn’t, and what we learned.
I hope the lessons in it can generalize and help builders across other ecosystems.
After more than two years onboarding all the major AVSs on EigenLayer and designing the EigenCloud, here is my candid reflection on what actually happened: what we got wrong, what we got right, and where we are going next.
What is even Restaking?
The fact that I am even explaining what this is means we did not do a good job explaining it when it was the main story. That alone is already lesson zero - have one story and tell it over and over again.
Our goal at Eigen has always been simple to say and hard to execute: increase what people can safely build onchain by making more offchain computation verifiable.
AVSs were our first, very opinionated attempt at this. 
An AVS is a proof of stake network that runs some offchain task with a set of decentralized operators. Those operators are watched, and if they misbehave, they get slashed. To slash anyone, you need capital at stake.
That is where restaking comes in. Instead of every AVS bootstrapping its own security from scratch, restaking lets you reuse staked ETH as security for AVSs. It lowers the capital cost and speeds up bootstrapping.
So the conceptual picture looked like this:
AVS: the service layer, where new PoS cryptoeconomically-secured systems live.
Restaking: the capital layer, reusing existing stake to secure those systems.
I still think the idea is elegant. But reality did not match the clean diagram. Several things simply did not land the way we hoped.
The Things That Did Not Land
1. We Picked A Market That Was Too Niche
We did not just want any verifiable compute. We fixated on systems that were decentralized, slashing based, and fully crypto economically secured from day one.
We wanted AVS to be infra services. Anyone can build AVSs like anyone can build SaaS. 
That sounds principled. It also drastically shrinks the set of teams who can realistically build on us.
The result was a market that was small, slow, and heavy: few potential customers, high implementation burden, and long timelines on both sides. These systems took months or years to build. That was true for EigenLayer infra and devtooling, and it was also true for each AVS on top.
Fast forward almost three years: we have two major AVSs live in production, Infura’s DIN and LayerZero’s EigenZero. That is not a flood of adoption. 
If I am honest, we designed for a world where teams wanted to start with crypto economic security and a decentralized operator set on day one. The world we actually got wanted something more incremental and application centric.
2. We Kept our Mouth Shut - for Regulatory
We started during the peak Gary Gensler era. Staking companies were being investigated and sued. Being “the restaking project” meant almost anything we said in public could be read as:
an investment promise, a yield ad, or something that deserved a subpoena.
That regulatory fog shaped how we could communicate. We could not talk as freely as we wanted. We could not correct misunderstandings in real time, even we are being absolutely murdered in the public opinion with the constant hit pieces and being thrown under the bus from partners. 
We could not casually say “this is not what happened” without weighing legal risk.
As a result, without much communication, we launched a locked token. Kind of wild to think about it sometimes.
If you ever felt like “Eigen seems evasive or oddly quiet about X,” there is a decent chance you were seeing the side effects of that environment. The risk of one wrong Tweet was nontrivial.
3. We Diluted The Brand - With The First AVSs
Most of Eigen’s early brand strength came from Sreeram – his energy, optimism, and belief that systems and people can be better. That pulled in a huge amount of goodwill. 
This goodwill then got reinforced with our billions of capital staked.
The way we comarketed the first wave of AVSs did not match that bar. Many early AVSs were loud and chasing narrative along. They were not the technically strongest or integrity wise best examples of what an AVS could be.
Downstream, people started to associate EigenLayer with whatever the latest farm or airdrop was. A lot of the skepticism, fatigue, and outright hate we still see today trace back to that period.
If I could rewind, I wish we started with fewer, higher conviction AVSs, be much pickier about who gets the brand halo, and be comfortable with a slower, less flashy rollout.
4. We Over Engineered on Tech for Trust Minimization
We tried to build the perfect general purpose slashing system – universal, flexible, expressive enough to capture any slashing condition to achieve trust minimization.
In practice, it meant we shipped slowly and spent a lot of time explaining a mechanism that most people were not ready for. Even now, we still have to do heavy education around the slashing system that shipped almost a year ago.
With hindsight, it would have been healthier to ship simpler slashing paths earlier, let different AVSs try narrower models, and then grow sophistication over time. Instead, we front loaded complexity and paid for it in speed and clarity.
The Things That Actually Worked
People like to skip straight to the “it failed” narrative. That is lazy. 
There are parts of this chapter that went extremely well, and they matter a lot for what comes next.
1. We Proved We Can Win In Brutal Markets
We prefer to play win-win but you don't want to f with us. We dominate in the market we choose to enter.
On restaking, Paradigm and Lido lined up behind a direct competitor. When they started, EigenLayer had less than 1B in TVL.
They had narrative firepower, distribution, capital, and everyone’s default trust. Many people believed and told me the combo would out-execute and steamroll us. That is not the universe we live in. We control 95% of the market in capital and 100% of tier 1 builders.
On data availability, we started later, with a smaller team and much less money. Incumbents had real head starts and strong marketing machines. Today EigenDA holds a very large share of the DA market by any metric that matters, and that share is set to grow exponentially as our largest partners fully go live.
Both of these markets were highly contested. In both, we came out ahead. 
2. EigenDA Became A Real Product That Shifted The Ecosystem
Shipping EigenDA on top of the EigenLayer infrastructure turned out to be a huge positive surprise.
It became the foundation of EigenCloud and gave Ethereum something it badly needed – a hyperscale DA lane so rollups can move fast without giving up the Ethereum universe for some new L1.
MegaETH was only started because they trusted Sreeram is going to unlock the DA bottleneck for them. The same was for Mantle when they first proposed to BitDAO to build an L2.
EigenDA also acts as a defensive shield for Ethereum. It is harder for external L1s to strap themselves to the Ethereum story and siphon value out when there is a native, high throughput DA solution available inside the ecosystem.
3. Accelerated the Preconf Market
One of the earliest talks for EigenLayer was around how EigenLayer could unlock preconfirmation on Ethereum. 
Ever since, preconf gathered a lot of attention with base rollup, but to implement it still remains a challenge. 
To accelerate the ecosystem, we are very excited to have also cofounded the Commit-Boost initiative which is trying to combat the lock-in effect from preconf clients and try to build the neutral platform for anyone to innovate through validator commitment.
Today, billions of dollars flow through Commit Boost and more than 35% validators are connected to it. We expect this number to go up more as major preconf services to go live in the coming months.
This is deeply important for anti-fragility of Ethereum ecosystem but also for continued innovation in the preconf market.
4. We Stayed Secure
We secured 10s of billions of assets for years.
That sounds boring and unremarkable until you realize how much infra in this space has blown up in one way or another. Avoiding that forced us to build real operational security, hire and train a world class security team, and internalize an adversarial mindset.
That culture matters for anything that touches user funds, AI, or real world systems. You cannot fake it later.
5. Lido Never Stayed Above 33 Percent
One underappreciated impact of the restaking era is how much ETH moved into LRT providers instead of pushing Lido far over 33 percent for a long time.
This mattered a lot for Ethereum’s social equilibrium. A world where Lido sat comfortably above 33 percent with no credible alternatives would have generated enormous governance drama and infighting.
Restaking and LRTs did not magically decentralize everything, but they did change the trajectory of stake concentration. That is not nothing.
5. We Got Clarity On Where The Real Frontier Is
The biggest “win” is conceptual.
We validated the thesis that the world needs more verifiable systems. What changed is our sense of the path. The path is probably not:
start with generalized crypto economic security, insist on a fully decentralized operator set from day one, and wait for everything to route through that layer.
The frontier grows faster when you hand developers direct tools to make their specific applications verifiable, and you match those tools with the right verification primitives. You meet builders where they are instead of asking them to become protocol designers from day one.
We started building our in-house modular services EigenCompute, EigenAI to address this demand. Things other teams raised 100s of Millions for and work for years, we can ship in months.
Where We Are Going
If you are still reading after all of this, thank you and send me a DM. I will give 10 people 100 dollars worth of ETH as a small thank you for caring about Eigen enough to go this deep.
So what do we do with all of this – the timing, the hits, the misses, the brand scars, the wins?
This section deserves its own analysis by itself, but here is a short version of where we are going and why:
1. Put EIGEN At The Center Of The System
Going forward, the entire EigenCloud and everything we build around it will be centered on the EIGEN token.
EIGEN is intended to be:
The core economic security driver for EigenCloud.
The asset that backs the various forms of risk being underwritten.
The main value capture instrument for the fee flows and economic activity across the platform.
A lot of early confusion came from the gap between what people assumed EIGEN would capture and what the actual mechanisms were. The next chapter is about closing that gap with concrete design and shipped systems. More on this later.
2. Let Developers Build Verifiable Applications, Not Just AVSs
The thesis is the same: increase what people can safely build onchain by making more offchain computation verifiable. But we won't use just one tool for verifiability.
Sometimes that will look like cryptoeconomic security. Sometimes it will be ZK proofs, TEEs, or hybrid approaches. The important part is not which technique you worship. The important part is that verifiability becomes a standard primitive you can plug into your stack.
Our job is to collapse the gap between:
“I have an app.”
and
“I have an app that users, counterparties, or regulators can actually verify.”
Given the current state of things, it is clear that cryptoeconomic + TEE is the undisputed winner when it comes to programmability (what developers can build) and security (not theoretical but practical security).
When ZK and other verification mechanisms are ready for developers, they will be incorporated in EigenCloud.
3. Go Deep On AI
The biggest shift in global compute right now is AI, especially agents. Crypto won’t be immune from it.
AI agents are effectively a language model wrapping tools to an environment where it can take actions. 
Today, not only is the language model a black box, but it's also not clear how agents are taking actions, and we've seen hacks already happen because you have to trust the developer. 
However, if the agent is verifiable, you no longer need to trust the developers. 
For an agent to be verifiable, the inference for the LLM part needs to be verifiable. It needs to have a verifiable compute environment to take actions in, and lastly, a verifiable data layer that will be able to store, retrieve, understand context. 
EigenCloud is built for this specific use case
EigenAI for deterministic and verifiable inference.
EigenCompute for verifiable actions
EigenDA for verifiable data storage and retrival.
We believe AI Agents is one of our strongest pitches for the verifiable cloud. 
That's why we are allocating a full team on this.
3. Build A Cleaner Story Around Stake And Yield
If you want real yield, you have to underwrite real risk.
We are exploring the broader space where stake supports:
Risk for smart contracts.
Risk for different classes of computation.
Risk that can be clearly described and priced.
Yield that actually reflects transparent, understandable risk being underwritten, not just whatever the current farming meta.
This naturally feeds back into how EIGEN is used, what it backs, and how value flows.
Some final words
Restaking did not turn into the “everything layer” some people, including myself, once hoped. It also did not vanish. It became what most first products become in a long journey:
A strong chapter. A source of hard earned lessons. And a piece of infra that now supports something broader.
We are still maintaining it. We still care about it. We are just refusing to be limited by that original narrative.
If you are a community member, an AVS builder, or an investor who still primarily associates Eigen with “that restaking thing,” I hope this gives you a clearer picture of what actually happened and where the ship is now pointed.
We are now entering a market with a much larger TAM around cloud services or going directly to developers - on the application side. We're tapping into AI that's specifically underexplored, and we're going to execute with the same intensity we've always worked with. 
The team remains extremely hungry, and I can't wait to prove all the doubters wrong.
I've never been more bullish on Eigen than I am now and I've only increased my EIGEN position and expect to continue doing so.
We are still on Day 1.