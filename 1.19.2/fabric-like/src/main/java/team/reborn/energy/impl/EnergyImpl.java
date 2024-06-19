package team.reborn.energy.impl;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyItem;

@ApiStatus.Internal
public class EnergyImpl {
	static {
		EnergyStorage.ITEM.registerFallback((stack, ctx) -> {
			if (stack.getItem() instanceof SimpleEnergyItem energyItem) {
				return SimpleEnergyItem.createStorage(ctx, energyItem.getEnergyCapacity(stack), energyItem.getEnergyMaxInput(stack), energyItem.getEnergyMaxOutput(stack));
			} else if (stack.getItem() instanceof SimpleEnergyItem battery) {
				return SimpleEnergyItem.createStorage(ctx, battery.getEnergyCapacity(stack), battery.getEnergyMaxInput(stack), battery.getEnergyMaxOutput(stack));
			} else {
				return null;
			}
		});
	}

	public static final EnergyStorage EMPTY = new EnergyStorage() {
		@Override
		public boolean supportsInsertion() {
			return false;
		}

		@Override
		public long insert(long maxAmount, TransactionContext transaction) {
			return 0;
		}

		@Override
		public boolean supportsExtraction() {
			return false;
		}

		@Override
		public long extract(long maxAmount, TransactionContext transaction) {
			return 0;
		}

		@Override
		public long getAmount() {
			return 0;
		}

		@Override
		public long getCapacity() {
			return 0;
		}
	};
}
